package com.stustirling.moviedbresponsehandling.shared.di

import android.content.Context
import com.stustirling.moviedbresponsehandling.shared.api.MovieDbApi
import com.stustirling.moviedbresponsehandling.shared.di.scopes.ForApplication
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Stu Stirling on 06/08/2017.
 */
@Singleton
@Component(
        modules = arrayOf(AppModule::class)
)
interface AppComponent {

    @ForApplication fun appContext() : Context
    fun movieDbApi() : MovieDbApi

}