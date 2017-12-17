package com.github.filipelipan.upcomingmovies.ui.movies_grid

import android.support.v7.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Created by lispa on 17/12/2017.
 */
object RxSearchObservable {

    fun fromView(searchView: SearchView): Observable<String> {

        val subject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return true
            }
        })

        return subject
    }
}// no instance