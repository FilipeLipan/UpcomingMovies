package com.github.filipelipan.upcomingmovies.ui.movie_detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.filipelipan.upcomingmovies.data.api.IRestApiService
import com.github.filipelipan.upcomingmovies.data.cache.GenreRepository
import com.github.filipelipan.upcomingmovies.error.IErrorHandlerHelper
import com.github.filipelipan.upcomingmovies.livedata_resources.PagedResource
import com.github.filipelipan.upcomingmovies.livedata_resources.Resource
import com.github.filipelipan.upcomingmovies.model.Genre
import com.github.filipelipan.upcomingmovies.model.Genres
import com.github.filipelipan.upcomingmovies.model.Movie
import com.github.filipelipan.upcomingmovies.model.PagedResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by lispa on 16/12/2017.
 */
class MovieDetailViewModel @Inject constructor(val genreRepository: GenreRepository) : ViewModel() {

    //TODO create a base view model class
    val disposables = CompositeDisposable();

    val mGenres = MutableLiveData<Resource<Map<Int, String>>>()

    //get genres and cache
    fun getGenres() {
        mGenres.value = Resource.loading(null)

        disposables.add(genreRepository.get("en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Genres>() {
                    override fun onComplete() {}

                    override fun onNext(genres: Genres) {
                        mGenres.value = Resource.success(genres.toMap())
                    }

                    override fun onError(e: Throwable) {
                        IErrorHandlerHelper.defaultErrorResolver(e);

                        //TODO refactor resource error to work better with rx error event bus
                        mGenres.value = Resource.error("", null)
                    }
                }))
    }

    fun detachView() {
        disposables.clear()
    }
}