package com.github.filipelipan.upcomingmovies.util.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.filipelipan.upcomingmovies.R

/**
 * Created by lispa on 29/11/2017.
 */
fun ImageView.loadImageFromURI(context: Context, url: String){
    Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(Glide.with(context)
                    .load(R.drawable.movie_placeholder))
            .into(this)
}

fun ImageView.loadAndCenterCropImageFromURI(context: Context, url: String){
    Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().centerCrop())
            .thumbnail(Glide.with(context)
                    .load(R.drawable.movie_placeholder))
            .into(this)
}

fun ImageView.setCircleImageURI(context: Context,url: String){
    Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
}