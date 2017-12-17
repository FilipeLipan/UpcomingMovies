package com.github.filipelipan.upcomingmovies.ui.movie_detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.graphics.drawable.VectorDrawableCompat
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.filipelipan.upcomingmovies.BuildConfig
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.model.Movie
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragment
import com.github.filipelipan.upcomingmovies.ui.movies_grid.MoviesGridFragment
import kotlinx.android.synthetic.main.a_include_movie_detail.*
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * Created by lispa on 17/12/2017.
 */
class MovieDetailFragment : BaseFragment<MovieDetailViewModel>() {

    private lateinit var mMovie: Movie;

    companion object {
        private val MOVIE_KEY = "movie_key"

        fun newInstance(movie: Movie) = MovieDetailFragment().apply {
            arguments = Bundle().apply { putParcelable(MOVIE_KEY, movie) }
        }
        val RELEASE_DATE_FORMAT = "yyyy-MM-dd";
    }

    override val mViewModel: MovieDetailViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)
    override val fragmentTag: String
        get() = MovieDetailFragment::class.java.simpleName
    override val fragmentName: String
        get() = getString(R.string.movie_detail)
    override val fragmentLayout: Int
        get() = R.layout.fragment_movie_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null && arguments!!.containsKey(MOVIE_KEY)) {
            mMovie = arguments!!.getParcelable(MOVIE_KEY)

            _vMovieTitleTV.text = mMovie.originalTitle

            _vReleaseDateTV.text = mMovie.releaseDate

            _vRateTV.text = mMovie.voteAverage.toString() + "/10"

            _vOverViewTV.text = mMovie.overview

            Glide.with(context)
                    .load(BuildConfig.BASE_POSTER_URL_HD + mMovie.posterPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(_vPosterKBV)
        }
    }
}