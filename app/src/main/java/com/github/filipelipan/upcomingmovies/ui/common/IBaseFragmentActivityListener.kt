package com.github.filipelipan.upcomingmovies.ui.common

import android.support.v4.app.Fragment

interface IBaseFragmentActivityListener {

    fun setTitle(title: String)

    fun setDisplayHomeAsUpEnabled()

    fun replaceFragment(fragment: Fragment)

    fun replaceAndBackStackFragment(fragment: Fragment)

    //fun replaceAndBackStackFragment(fragment: Fragment, animated: Boolean)
}
