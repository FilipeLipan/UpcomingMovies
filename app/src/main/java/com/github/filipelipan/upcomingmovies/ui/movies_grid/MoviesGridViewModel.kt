package com.github.filipelipan.upcomingmovies.ui.movies_grid

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.filipelipan.upcomingmovies.data.api.IRestApiService
import com.github.filipelipan.upcomingmovies.data.cache.GenreRepository
import com.github.filipelipan.upcomingmovies.error.IErrorHandlerHelper
import com.github.filipelipan.upcomingmovies.livedata_resources.PagedResource
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
class MoviesGridViewModel @Inject constructor(val restApi: IRestApiService, val genreRepository: GenreRepository) : ViewModel() {
    var nextPage = 1

    var search = ""

    val disposables = CompositeDisposable();

    val mMovies = MutableLiveData<PagedResource<ArrayList<Movie>>>()

    fun getMovies(searchQuery: String,minDate: String, isLoadMore: Boolean) {
        if(isLoadMore){
            nextPage += 1
        }else{
            nextPage = 1
            if(mMovies.value != null && mMovies.value!!.data != null) {
                mMovies.value = PagedResource.loading(mMovies.value!!.data)
            }else{
                mMovies.value = PagedResource.loading(ArrayList<Movie>())
            }
        }

        search = searchQuery

        if(search.equals("")){
            addDisposable(restApi.getMoviesList(nextPage.toString(), "en-US", minDate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(movieSubscriber(isLoadMore)));
        }else{
            addDisposable(restApi.searchMovie(nextPage.toString(), searchQuery, "en-US", minDate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(movieSubscriber(isLoadMore)));
        }
    }

    fun movieSubscriber(isLoadMore: Boolean) : DisposableObserver<PagedResponse<Movie>> {
        return object : DisposableObserver<PagedResponse<Movie>>() {
            override fun onError(e: Throwable) {
                IErrorHandlerHelper.defaultErrorResolver(e);

                //TODO --improved-- make
                if(mMovies.value != null && mMovies.value!!.data != null) {
                    mMovies.value = PagedResource.error(e.message!!, mMovies.value!!.data!!)
                }else{
                    mMovies.value = PagedResource.error(e.message!!, ArrayList<Movie>())
                }
            }

            override fun onComplete() {
            }

            override fun onNext(movies: PagedResponse<Movie>) {
                if(isLoadMore){
                    mMovies.value = PagedResource.successMoreData(mMovies.value!!.data, movies.results, nextPage < movies.totalPages)
                }else{
                    mMovies.value = PagedResource.success(movies.results, nextPage < movies.totalPages)
                }
            }
        }
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun detachView() {
        disposables.clear()
    }
}