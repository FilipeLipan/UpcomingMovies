package com.github.filipelipan.upcomingmovies.ui.movies_grid

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.livedata_resources.Status.*
import com.github.filipelipan.upcomingmovies.model.Movie
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragment
import com.github.filipelipan.upcomingmovies.ui.custom.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_movies_grid.*
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * Created by lispa on 16/12/2017.
 */
class MoviesGridFragment : BaseFragment<MoviesGridViewModel>(),BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    val participantsAdapter by lazy {
        MoviesAdapter(context, ArrayList<Movie>())
    }

    val minDate by lazy {
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

        initAdapter()

        _vSwipeRefreshLayout.setOnRefreshListener(this)

        mViewModel.getMovies(minDate, false);

        mViewModel.mMovies.observe(this, Observer {

            if (it != null) {
                when (it.status) {
                    SUCCESS -> {
                        participantsAdapter.setEnableLoadMore(false)
                        //TODO set up empty view
                        participantsAdapter.setNewData(it.data)
//            participantsAdapter.setEmptyView(getAppActivityListener().inflateView(R.layout.include_empty_view, participantsRecycler))
                        _vSwipeRefreshLayout.setRefreshing(false)

                        //TODO make logic to discover if all itens are loaded
//            participantsAdapter.setEnableLoadMore(!complete)
                        participantsAdapter.setEnableLoadMore(true)
                    }
                    SUCCESS_LOAD_MORE_DATA -> {
                        _vSwipeRefreshLayout.setEnabled(false)
                        if (!it.hasMorePages) {
                            participantsAdapter.addData(it.data!!)
                            participantsAdapter.loadMoreEnd(true)
                            _vSwipeRefreshLayout.setEnabled(true)
                        } else {
                            participantsAdapter.addData(it.data!!)
                            participantsAdapter.loadMoreComplete()
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

    private fun initAdapter() {
        participantsAdapter.setOnLoadMoreListener(this, _vMoviesRecyclerRV)
        participantsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        participantsAdapter.setLoadMoreView(CustomLoadMoreView())
        _vMoviesRecyclerRV.layoutManager = GridLayoutManager(context, calculateNoOfColumns(context!!))
        _vMoviesRecyclerRV.setAdapter(participantsAdapter)
    }

    override fun onLoadMoreRequested() {
        _vSwipeRefreshLayout.setEnabled(false)
        mViewModel.getMovies(minDate, true);
    }

    override fun onRefresh() {
        mViewModel.getMovies(minDate, false);
    }


    /**
     * Calculate the number of columns for the Gridview
     *
     * @param context Used to access the DisplayMetrics
     * @return An int resulting from the division between the screen width and a given dp.
     */
    fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        var noOfColumns = (dpWidth / 180).toInt()

        if (noOfColumns <= 0) {
            noOfColumns = 1
        }
        return noOfColumns
    }

}