package com.github.filipelipan.upcomingmovies.util.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.filipelipan.upcomingmovies.R

//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
//
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
//
//fun ImageView.setImageURIFit(url: String){
//    Glide.with(context)
//            .load(url)
//            .apply(RequestOptions.fitCenterTransform())
//            .into(this)
//}
//
//fun ImageView.setCircleImageURI( url: String){
//    Glide.with(context)
//            .load(url)
//            .apply(RequestOptions.circleCropTransform())
//            .into(this)
//}