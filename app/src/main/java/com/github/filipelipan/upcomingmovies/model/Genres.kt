package com.github.filipelipan.upcomingmovies.model

/**
 * Created by lispa on 21/12/2017.
 */
open class Genres {
    open var genres: ArrayList<Genre> = ArrayList()

    fun toMap() : Map<Int, String> {
        return genres.map { it.id to it.name }.toMap()
    }
}