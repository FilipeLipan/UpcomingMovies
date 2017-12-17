package com.github.filipelipan.upcomingmovies.model

import com.google.gson.annotations.SerializedName

/**
 * Created by lispa on 13/12/2017.
 */
open class PagedResponse<T> {
    @SerializedName("total_results")
    open var totalResults: Int = 0
    @SerializedName("total_pages")
    open var totalPages: Int = 0
    open var page: Int = 1
    open var results: ArrayList<T> = ArrayList()
}
