package com.github.filipelipan.upcomingmovies.ui.movies_grid

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.filipelipan.upcomingmovies.BuildConfig
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.model.Movie

/**
 * Created by lispa on 16/12/2017.
 */

class MoviesAdapter (mContext: Context?, mMoviesList: List<Movie>) :
        BaseQuickAdapter<Movie, BaseViewHolder>(R.layout.list_item, mMoviesList) {


    override fun convert(viewHolder: BaseViewHolder, item: Movie) {

        //TODO insert placeholder

        mContext?.let {
            Glide.with(mContext)
                    .load(BuildConfig.BASE_POSTER_URL + item.posterPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(viewHolder.getView(R.id._vMovieGridItemIV))
        }
    }

}
