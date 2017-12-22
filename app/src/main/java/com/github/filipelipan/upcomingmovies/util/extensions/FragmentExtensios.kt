package com.github.filipelipan.upcomingmovies.util.extensions

import android.content.Context
import android.support.v4.app.Fragment

/**
 * Created by lispa on 17/12/2017.
 */

/**
 * Calculate the number of columns for the Gridview
 *
 * @param context Used to access the DisplayMetrics
 * @return An int resulting from the division between the screen width and a given dp.
 */
fun Fragment.calculateNoOfColumns(): Int {
    val displayMetrics = context!!.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    var noOfColumns = (dpWidth / 180).toInt()

    if (noOfColumns <= 0) {
        noOfColumns = 1
    }
    return noOfColumns
}