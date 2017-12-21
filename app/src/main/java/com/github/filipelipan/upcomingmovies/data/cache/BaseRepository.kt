package com.github.filipelipan.upcomingmovies.data.cache

import com.github.filipelipan.upcomingmovies.data.api.IRestApiService
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import java.io.File

/**
 * Created by lispa on 21/12/2017.
 */
abstract class BaseRepository (protected val cacheDir: File, protected val restApi: IRestApiService) {

    protected val providers: Providers = RxCache.Builder()
            .persistence(cacheDir, GsonSpeaker())
            .using(Providers::class.java)

}
