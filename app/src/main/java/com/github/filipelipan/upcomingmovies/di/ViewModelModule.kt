package com.github.filipelipan.upcomingmovies.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.filipelipan.upcomingmovies.ui.movie_detail.MovieDetailViewModel
import com.github.filipelipan.upcomingmovies.ui.movies_grid.MoviesGridViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MoviesGridViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: MoviesGridViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
