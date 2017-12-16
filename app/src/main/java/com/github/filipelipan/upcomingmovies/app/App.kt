package com.github.filipelipan.upcomingmovies.app

import android.app.Activity
import android.app.Application
import android.app.Service
import android.support.v7.app.AppCompatDelegate
import com.github.filipelipan.upcomingmovies.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>


    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        instance = this

        AppInjector.init(this)
    }

    companion object {
        var instance: App? = null
            private set
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun serviceInjector(): AndroidInjector<Service> = dispatchingServiceInjector
}