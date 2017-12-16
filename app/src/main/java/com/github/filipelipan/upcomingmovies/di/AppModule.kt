package com.github.filipelipan.upcomingmovies.di

import android.app.Application
import android.arch.persistence.room.Room
import com.github.filipelipan.upcomingmovies.app.App
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideApp(): App = App.instance!!

}
