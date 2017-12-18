package com.github.filipelipan.upcomingmovies.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import com.github.filipelipan.upcomingmovies.R
import com.github.filipelipan.upcomingmovies.model.Movie
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragment
import com.github.filipelipan.upcomingmovies.ui.common.BaseFragmentActivity
import com.github.filipelipan.upcomingmovies.ui.movie_detail.MovieDetailFragment
import com.github.filipelipan.upcomingmovies.ui.movies_grid.MoviesGridFragment
import kotlinx.android.synthetic.main.include_container.*
import kotlinx.android.synthetic.main.include_toolbar.*

val MOVIE_KEY = "movie_key"
class DetailActivity : BaseFragmentActivity<ViewModel>() {

    companion object {
        fun getIntent(context: Context, movie: Movie) : Intent{
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_KEY, movie)
            return intent
        }
    }

    override val mViewModel: ViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
    override val activityName: String
        get() = ""
    override val activityLayout: Int
        get() = R.layout.activity_detail
    override val container: FrameLayout
        get() = _vContainer
    override val toolbar: Toolbar
        get() = _vToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null){
            if(supportFragmentManager.findFragmentByTag(MovieDetailFragment.fragmentStaticTag) != null){
                replaceFragment(supportFragmentManager.findFragmentByTag(MovieDetailFragment.fragmentStaticTag))
            }else{
                addFragment(MovieDetailFragment.newInstance(intent.getSerializableExtra(MOVIE_KEY) as Movie))
            }
        }
    }
}
