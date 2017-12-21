package com.github.filipelipan.upcomingmovies.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.error.ErrorEvents
import com.github.filipelipan.upcomingmovies.error.RxErrorEventBus
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragmentActivity
import com.github.filipelipan.upcomingmovies.ui.movies_grid.MoviesGridFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.github.filipelipan.upcomingmovies.error.RxHttpError
import com.github.filipelipan.upcomingmovies.util.extensions.toast
import java.net.HttpURLConnection


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

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null){
            if(supportFragmentManager.findFragmentByTag(MoviesGridFragment.fragmentStaticTag) != null){
                replaceFragment(supportFragmentManager.findFragmentByTag(MoviesGridFragment.fragmentStaticTag))
            }else{
                addFragment(MoviesGridFragment.newInstance())
            }
        }

        disposables.add(RxErrorEventBus.toObservable(ErrorEvents.HttpError::class.java)
                .subscribe { t ->
                    val error = t.error
                    when (error.errorCode) {
                        RxHttpError.SOCKETTIMEOUT_CODE, RxHttpError.UNKNOWNHOST_CODE, RxHttpError.NO_CONNECTIVITY_CODE -> {
                            toast(getString(R.string.err_connection_error))
                        }
                        HttpURLConnection.HTTP_FORBIDDEN, HttpURLConnection.HTTP_UNAUTHORIZED -> {
                            toast(getString(R.string.err_unauthorized))
                        }
                        else ->
                            if(error.detail != null){
                                toast(error.detail!!)
                            }
                    }
                })

        disposables.add(RxErrorEventBus.toObservable(ErrorEvents.ThrowableError::class.java)
                .subscribe { t ->
                    var error = t.error
                    error.printStackTrace()
                    toast(error.message!!)
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
