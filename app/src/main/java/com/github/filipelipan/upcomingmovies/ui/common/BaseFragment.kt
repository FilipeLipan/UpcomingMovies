package com.github.filipelipan.upcomingmovies.ui.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.filipelipan.upcomingmovies.di.Injectable
import javax.inject.Inject

abstract class BaseFragment<out V: ViewModel> : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected var appActivityListener: IBaseFragmentActivityListener? = null
        private set

    abstract val mViewModel: V
    abstract val fragmentTag: String
    abstract val fragmentName: String
    abstract val fragmentLayout: Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appActivityListener = context as IBaseFragmentActivityListener?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(fragmentLayout, container, false)

}