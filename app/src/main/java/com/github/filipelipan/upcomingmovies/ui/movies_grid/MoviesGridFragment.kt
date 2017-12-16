package com.github.filipelipan.upcomingmovies.ui.movies_grid

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragment

/**
 * Created by lispa on 16/12/2017.
 */
class MoviesGridFragment : BaseFragment<MoviesGridViewModel>() {

    companion object {
        fun newInstance() = MoviesGridFragment()
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


    }
}