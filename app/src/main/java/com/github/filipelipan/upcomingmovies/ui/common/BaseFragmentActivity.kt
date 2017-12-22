package com.github.filipelipan.upcomingmovies.ui.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseFragmentActivity<V : ViewModel> : AppCompatActivity(), IBaseFragmentActivityListener, HasSupportFragmentInjector {

    @Inject
    protected lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract val mViewModel: V
    abstract val activityName: String
    abstract val activityLayout: Int
    abstract val container: FrameLayout
    abstract val toolbar : Toolbar

    private val fragmentTransaction: FragmentTransaction
        get() = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)
        setSupportActionBar(toolbar)
    }

    override fun setTitle(title: String) {
        supportActionBar!!.title = title
    }

    override fun setDisplayHomeAsUpEnabled() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun replaceFragment(fragment: Fragment) {
        val ft = fragmentTransaction
        if (fragment is BaseFragment<*>) {
            ft.replace(container!!.id, fragment, fragment.fragmentTag)
        } else {
            ft.replace(container!!.id, fragment, fragment.javaClass.simpleName)
        }

        ft.commit()
    }

    override fun addFragment(fragment: Fragment) {
        val ft = fragmentTransaction
        if (fragment is BaseFragment<*>) {
            ft.add(container!!.id, fragment, fragment.fragmentTag)
        } else {
            ft.add(container!!.id, fragment, fragment.javaClass.simpleName)
        }

        ft.commit()
    }

    override fun replaceAndBackStackFragment(fragment: Fragment) {
        val ft = fragmentTransaction

        if (fragment is BaseFragment<*>) {
            ft.replace(container!!.id, fragment, fragment.fragmentTag)
            ft.addToBackStack(fragment.fragmentTag)
        } else {
            ft.replace(container!!.id, fragment, fragment.javaClass.simpleName)
            ft.addToBackStack(fragment.javaClass.simpleName)
        }

        ft.commit()

    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> =
            dispatchingAndroidInjector
}