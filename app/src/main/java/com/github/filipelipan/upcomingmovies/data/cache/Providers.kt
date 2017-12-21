package com.github.filipelipan.upcomingmovies.data.cache

import java.util.concurrent.TimeUnit
import com.github.filipelipan.upcomingmovies.model.Genre
import com.github.filipelipan.upcomingmovies.model.Genres
import io.reactivex.Observable
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictProvider
import io.rx_cache2.LifeCache

/**
 * Created by lispa on 21/12/2017.
 */
interface Providers {
    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    fun getGenres(apiRequest: Observable<Genres>, key: DynamicKey, evictProvider: EvictProvider): Observable<Genres>
}