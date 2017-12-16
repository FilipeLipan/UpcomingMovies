package com.github.filipelipan.upcomingmovies.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragment
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragmentActivity
import com.github.filipelipan.upcomingmovies.ui.movies_grid.MoviesGridFragment
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : BaseFragmentActivity<ViewModel>() {

    override val mViewModel: ViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    override val activityName: String
        get() = getString(R.string.main_activity_name)
    override val activityLayout: Int
        get() = R.layout.activity_main
    override val container: FrameLayout
        get() = _vContainer
    override val toolbar: Toolbar
        get() = _vToolbar
    override val initialFragment: BaseFragment<*>
        get() = MoviesGridFragment.newInstance()


}
