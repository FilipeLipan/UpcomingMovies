package com.github.filipelipan.upcomingmovies.data.api

import com.github.filipelipan.upcomingmovies.model.Movie
import com.github.filipelipan.upcomingmovies.model.PagedResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lispa on 13/12/2017.
 */
public interface IRestApiService {

    companion object {
        const val SORTED_BY_MOST_POPULAR = "primary_release_date.asc"
        const val SORTED_BY = "sort_by"
    }

    @GET("discover/movie")
    @Headers(SORTED_BY + ":" + SORTED_BY_MOST_POPULAR)
    fun getMoviesList(@Query("page") page: String,
                     @Query("language") language: String,
                     @Query("primary_release_date.gte") minReleaseDate: String) : Observable<PagedResponse<Movie>>

    @GET("search/movie")
    fun searchMovie(@Query("page") page: String,
                     @Query("query") query: String,
                     @Query("language") language: String) : Observable<PagedResponse<Movie>>
}