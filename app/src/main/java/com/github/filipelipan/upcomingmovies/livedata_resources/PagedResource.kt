package com.github.filipelipan.upcomingmovies.livedata_resources

/**
 * Created by lispa on 16/12/2017.
 */
class PagedResource<T> private constructor(val status: Status, val data: T?, val message: String?, val hasMorePages: Boolean) {
    companion object {

        fun <T> success(data: T, hasMorePages: Boolean): PagedResource<T> {
            return PagedResource(Status.SUCCESS, data, null, hasMorePages)
        }

        fun <T> successMoreData(data: T, hasMorePages: Boolean): PagedResource<T> {
            return PagedResource(Status.SUCCESS_LOAD_MORE_DATA, data, null, hasMorePages)
        }

        fun <T> error(msg: String, data: T?): PagedResource<T> {
            return PagedResource(Status.ERROR, data, msg, false)
        }

        fun <T> loading(data: T?): PagedResource<T> {
            return PagedResource(Status.LOADING, data, null, false)
        }
    }
}
