package com.github.filipelipan.upcomingmovies.di
import android.app.Application
import com.github.filipelipan.upcomingmovies.app.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
                AndroidInjectionModule::class,
                AppModule::class,
                ActivityBindsModule::class,
                ViewModelModule::class,
                FragmentBuildersModule::class
        ))
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)

}