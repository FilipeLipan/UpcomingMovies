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


/**
 * Created by lispa on 16/12/2017.
 */
class MoviesGridFragment : BaseFragment<MoviesGridViewModel>(),BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    lateinit var mSearchView: SearchView

    var compositeDisposable = CompositeDisposable()

    val mMoviesAdapter by lazy {
        MoviesAdapter(context, ArrayList<Movie>())
    }

    val mMinDate by lazy {
        SimpleDateFormat(RELEASE_DATE_FORMAT).format(Calendar.getInstance().getTime())
    }

    companion object {
        fun newInstance() = MoviesGridFragment()
        val RELEASE_DATE_FORMAT = "yyyy-MM-dd";
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
            mViewModel.getMovies(mViewModel.search,mMinDate, false);
        }

        mViewModel.mMovies.observe(this, Observer {

            if (it != null) {
                when (it.status) {
                    SUCCESS -> {
                        mMoviesAdapter.setEnableLoadMore(false)
                        //TODO set up empty view
                        mMoviesAdapter.setNewData(it.data)
//            mMoviesAdapter.setEmptyView(getAppActivityListener().inflateView(R.layout.include_empty_view, participantsRecycler))
                        _vSwipeRefreshLayout.setRefreshing(false)

                        //TODO make logic to discover if all itens are loaded
//            mMoviesAdapter.setEnableLoadMore(!complete)
                        mMoviesAdapter.setEnableLoadMore(true)
                    }
                    SUCCESS_LOAD_MORE_DATA -> {
                        _vSwipeRefreshLayout.setEnabled(false)
                        if (!it.hasMorePages) {
                            mMoviesAdapter.addData(it.newData!!)
                            mMoviesAdapter.loadMoreEnd(true)
                            _vSwipeRefreshLayout.setEnabled(true)
                        } else {
                            mMoviesAdapter.addData(it.newData!!)
                            mMoviesAdapter.loadMoreComplete()
                            _vSwipeRefreshLayout.setEnabled(true)
                        }
                    }
                    LOADING -> _vSwipeRefreshLayout.setEnabled(true)
                    ERROR -> {
                        _vSwipeRefreshLayout.setEnabled(false)
//                        DialogUtil.SimpleDialog(this, 0, "Aviso", booleanResource.message)
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.movies_search_menu, menu);

        val item = menu?.findItem(R.id.search_movies)

        mSearchView = item?.actionView as SearchView
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
                        mViewModel.getMovies(searchText,mMinDate, false);
                    }
                }))
    }

    private fun initAdapter() {
        mMoviesAdapter.setOnLoadMoreListener(this, _vMoviesRecyclerRV)
        mMoviesAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mMoviesAdapter.setLoadMoreView(CustomLoadMoreView())
        _vMoviesRecyclerRV.layoutManager = GridLayoutManager(context, calculateNoOfColumns())
        _vMoviesRecyclerRV.setAdapter(mMoviesAdapter)
    }

    override fun onLoadMoreRequested() {
        _vSwipeRefreshLayout.setEnabled(false)
        mViewModel.getMovies(mViewModel.search,mMinDate, true);
    }

    override fun onRefresh() {
        mViewModel.getMovies(mSearchView.query.toString(),mMinDate, false);
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