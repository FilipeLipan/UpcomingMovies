package com.github.filipelipan.upcomingmovies.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.ui.common.BaseActivity
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragment
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragmentActivity

class MainActivity : BaseFragmentActivity<ViewModel>() {

    override val mViewModel: ViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    override val activityName: String
        get() = getString(R.string.main_activity_name)
    override val activityLayout: Int
        get() = R.layout.activity_main
    override val container: FrameLayout
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val toolbar: Toolbar
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val initialFragment: BaseFragment<*>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


}
