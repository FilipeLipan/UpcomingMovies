package com.github.filipelipan.upcomingmovies.ui.movies_grid

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.livedata_resources.Status.*
import com.github.filipelipan.upcomingmovies.model.Movie
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragment
import com.github.filipelipan.upcomingmovies.ui.custom.CustomLoadMoreView
import com.github.filipelipan.upcomingmovies.util.extensions.calculateNoOfColumns
import kotlinx.android.synthetic.main.fragment_movies_grid.*
import java.text.SimpleDateFormat
import java.util.Calendar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.github.filipelipan.upcomingmovies.ui.movie_detail.MovieDetailFragment

/**
 * Created by lispa on 16/12/2017.
 */
class MoviesGridFragment : BaseFragment<MoviesGridViewModel>(),BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    lateinit var mSearchView: SearchView

    var mShouldReload: Boolean = false

    var compositeDisposable = CompositeDisposable()

    val mMoviesAdapter by lazy {
        MoviesAdapter(context, ArrayList<Movie>())
    }

    val mMinDate by lazy {
        SimpleDateFormat(RELEASE_DATE_FORMAT).format(Calendar.getInstance().time)
    }

    companion object {
        fun newInstance() = MoviesGridFragment()
        val RELEASE_DATE_FORMAT = "yyyy-MM-dd"
    }

    override val mViewModel: MoviesGridViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MoviesGridViewModel::class.java)
    override val fragmentTag: String
        get() = MoviesGridFragment::class.java.simpleName
    override val fragmentName: String
        get() = getString(R.string.movies_grid_title)
    override val fragmentLayout: Int
        get() = R.layout.fragment_movies_grid

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
//        appActivityListener!!.setTitle("Movies")

        initAdapter()

        _vSwipeRefreshLayout.setOnRefreshListener(this)

        if(savedInstanceState == null) {
            mViewModel.getMovies(mViewModel.search,mMinDate, false)
        }else{
            mShouldReload = true
        }

        mMoviesAdapter.setOnItemClickListener(object: BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                appActivityListener!!.replaceAndBackStackFragment(MovieDetailFragment.newInstance(adapter!!.getItem(position) as Movie))
            }
        })


        mViewModel.mMovies.observe(this, Observer {

            if(mShouldReload){
                mMoviesAdapter.setNewData(it!!.data)
                mShouldReload = false
            }else {

                if (it != null) {
                    when (it.status) {
                        SUCCESS -> {
                            mMoviesAdapter.setEnableLoadMore(false)
                            //TODO set up empty view
                            mMoviesAdapter.setNewData(it.data)
//            mMoviesAdapter.setEmptyView(getAppActivityListener().inflateView(R.layout.include_empty_view, participantsRecycler))
                            _vSwipeRefreshLayout.isRefreshing = false

                            //TODO make logic to discover if all itens are loaded
//            mMoviesAdapter.setEnableLoadMore(!complete)
                            mMoviesAdapter.setEnableLoadMore(true)
                        }
                        SUCCESS_LOAD_MORE_DATA -> {
                            _vSwipeRefreshLayout.isEnabled = false
                            if (!it.hasMorePages) {
                                mMoviesAdapter.addData(it.newData!!)
                                mMoviesAdapter.loadMoreEnd(true)
                                _vSwipeRefreshLayout.isEnabled = true
                            } else {
                                mMoviesAdapter.addData(it.newData!!)
                                mMoviesAdapter.loadMoreComplete()
                                _vSwipeRefreshLayout.isEnabled = true
                            }
                        }
                        LOADING -> _vSwipeRefreshLayout.isEnabled = true
                        ERROR -> {
                            _vSwipeRefreshLayout.isEnabled = false
//                        DialogUtil.SimpleDialog(this, 0, "Aviso", booleanResource.message)
                        }
                    }
                }
            }
        })
    }

    private fun initAdapter() {
        mMoviesAdapter.setOnLoadMoreListener(this, _vMoviesRecyclerRV)
        mMoviesAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mMoviesAdapter.setLoadMoreView(CustomLoadMoreView())
        _vMoviesRecyclerRV.layoutManager = GridLayoutManager(context, calculateNoOfColumns())
        _vMoviesRecyclerRV.adapter = mMoviesAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.movies_search_menu, menu)

        val searchItem = menu?.findItem(R.id.search_movies)

        mSearchView = searchItem?.actionView as SearchView

        if (!mViewModel.search.isEmpty()) {
            searchItem.expandActionView()
            mSearchView.setQuery(mViewModel.search, false)
            mSearchView.clearFocus()
        }

        setUpSearch()
    }

    private fun setUpSearch(){
        compositeDisposable.add(RxSearchObservable.fromView(mSearchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(object : Predicate<String> {
                    @Throws(Exception::class)
                    override fun test(searchText: String): Boolean {
                        return !searchText.isEmpty()
                    }
                })
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<String> {
                    @Throws(Exception::class)
                    override fun accept(searchText: String) {
                        mViewModel.getMovies(searchText,mMinDate, false)
                    }
                }))
    }
    override fun onLoadMoreRequested() {
        _vSwipeRefreshLayout.isEnabled = false
        mViewModel.getMovies(mViewModel.search,mMinDate, true)
    }

    override fun onRefresh() {
        mViewModel.getMovies(mSearchView.query.toString(),mMinDate, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.search = mSearchView.query.toString()
    }

    //TODO find solution to access view model in onDestroy
//    override fun onDestroy() {
//        super.onDestroy()
//        compositeDisposable.clear()
//
//        //TODO refactor this
//        mViewModel.detachView()
//    }
}