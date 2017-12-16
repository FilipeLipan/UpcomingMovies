package com.github.filipelipan.upcomingmovies.di

import com.github.filipelipan.upcomingmovies.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindsModule {

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    abstract fun contributeMainActivity(): MainActivity
}