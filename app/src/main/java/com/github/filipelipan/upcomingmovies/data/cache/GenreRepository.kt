package com.github.filipelipan.upcomingmovies.data.cache

import com.github.filipelipan.upcomingmovies.data.api.IRestApiService
import com.github.filipelipan.upcomingmovies.model.Genres
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictProvider
import java.io.File

/**
 * Created by lispa on 21/12/2017.
 */
class GenreRepository(cacheDir: File, restApi: IRestApiService) : BaseRepository(cacheDir, restApi) {

    fun get(language: String): Observable<Genres> {
        return providers.getGenres(restApi.getGenres(language),
                DynamicKey("id"),
                EvictProvider(false));
    }
}