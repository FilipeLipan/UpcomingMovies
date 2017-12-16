package com.github.filipelipan.upcomingmovies.data.api

import com.github.filipelipan.upcomingmovies.model.Movie
import com.github.filipelipan.upcomingmovies.model.PagedResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by lispa on 13/12/2017.
 */
public interface IRestApiService {

    @GET("discover/movie")
    fun getMovieList() : Observable<PagedResponse<Movie>>
}