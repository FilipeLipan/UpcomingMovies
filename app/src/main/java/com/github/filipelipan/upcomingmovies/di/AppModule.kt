package com.github.filipelipan.upcomingmovies.di

import com.github.filipelipan.upcomingmovies.app.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideApp(): App = App.instance!!

}
