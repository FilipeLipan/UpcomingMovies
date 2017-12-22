package com.github.filipelipan.upcomingmovies.di

import com.github.filipelipan.upcomingmovies.BuildConfig
import com.github.filipelipan.upcomingmovies.app.App
import com.github.filipelipan.upcomingmovies.data.api.IRestApiService
import com.github.filipelipan.upcomingmovies.data.cache.GenreRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideApp(): App = App.instance!!

    @Reusable
    @Provides
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Reusable
    @Provides
    internal fun provideGson(): Gson {
        return GsonBuilder()
               .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
               .create()
    }

    @Reusable
    @Provides
    internal fun provideIRestApiService(retrofit: Retrofit): IRestApiService {
        return retrofit.create(IRestApiService::class.java)
    }

    @Reusable
    @Provides
    internal fun provideOkHttpClient(interceptor: Interceptor,
                                     loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        if (BuildConfig.DEBUG) {

            return OkHttpClient.Builder()
                    //Http authorize interceptor
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .readTimeout(50, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()
        } else {
            return OkHttpClient.Builder()
                    .build()
        }
    }

    @Reusable
    @Provides
    internal fun providerLogginInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Reusable
    @Provides
    internal fun providerApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.MOVIES_DB_API_KEY)
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Reusable
    @Provides
    internal fun providerGenreRepository(restApi: IRestApiService): GenreRepository {
        return GenreRepository(provideApp().cacheDir, restApi)
    }

}
