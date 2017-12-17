package com.github.filipelipan.upcomingmovies.ui.movie_detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.filipelipan.upcomingmovies.data.api.IRestApiService
import com.github.filipelipan.upcomingmovies.livedata_resources.PagedResource
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
class MovieDetailViewModel @Inject constructor() : ViewModel()