package com.github.filipelipan.upcomingmovies.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Created by lispa on 13/12/2017.
 */

open class Movie : Serializable {
    open var id: Int = 0
    @SerializedName("original_title")
    open var originalTitle: String = ""
    @SerializedName("poster_path")
    open var posterPath: String = ""
    open var overview: String = ""
    @SerializedName("vote_average")
    open var voteAverage: Double = 0.toDouble()
    @SerializedName("release_date")
    open var releaseDate: String = ""
    @SerializedName("genre_ids")
    open var genreIds: ArrayList<Int> = ArrayList()
}