package com.github.filipelipan.upcomingmovies.ui.movie_detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.graphics.drawable.VectorDrawableCompat
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.filipelipan.upcomingmovies.BuildConfig
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.livedata_resources.Status
import com.github.filipelipan.upcomingmovies.model.Movie
import com.github.filipelipan.upcomingmovies.ui.MOVIE_KEY
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragment
import com.github.filipelipan.upcomingmovies.ui.movies_grid.MoviesGridFragment
import com.github.filipelipan.upcomingmovies.util.extensions.inflateView
import com.github.filipelipan.upcomingmovies.util.extensions.loadImageFromURI
import kotlinx.android.synthetic.main.a_include_movie_detail.*
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.fragment_movies_grid.*

/**
 * Created by lispa on 17/12/2017.
 */
class MovieDetailFragment : BaseFragment<MovieDetailViewModel>() {

    private lateinit var mMovie: Movie;

    companion object {
        val fragmentStaticTag = MovieDetailFragment::class.java.simpleName

        fun newInstance(movie: Movie) = MovieDetailFragment().apply {
            arguments = Bundle().apply { putSerializable(MOVIE_KEY, movie) }
        }
    }

    override val mViewModel: MovieDetailViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)
    override val fragmentTag: String
        get() = fragmentStaticTag
    override val fragmentName: String
        get() = getString(R.string.movie_detail)
    override val fragmentLayout: Int
        get() = R.layout.fragment_movie_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appActivityListener!!.setTitle(getString(R.string.movie_detail_title))

        if (arguments != null && arguments!!.containsKey(MOVIE_KEY)) {
            mMovie = arguments!!.getSerializable(MOVIE_KEY) as Movie

            _vMovieTitleTV.text = mMovie.originalTitle

            _vReleaseDateTV.text = mMovie.releaseDate

            //TODO -- improve -- improve vote Average layout and remove string /10
            _vRateTV.text = mMovie.voteAverage.toString() + "/10"

            _vOverViewTV.text = mMovie.overview

            //TODO -- improve -- fix gender no showing up
            //TODO -- improve -- handle empty textviews
            _vPosterKBV.loadImageFromURI(context!!, BuildConfig.BASE_POSTER_URL_HD + mMovie.posterPath)
        }


        mViewModel.mGenres.observe(this, Observer {

            when (it!!.status) {
                Status.SUCCESS -> {

                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    _vGenresTV.text = ("Not available")
                }
            }
        })
    }
}