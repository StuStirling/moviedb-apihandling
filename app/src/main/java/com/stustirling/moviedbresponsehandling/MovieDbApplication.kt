package com.stustirling.moviedbresponsehandling

import android.app.Application
import com.stustirling.moviedbresponsehandling.shared.di.AppComponent
import com.stustirling.moviedbresponsehandling.shared.di.AppModule
import com.stustirling.moviedbresponsehandling.shared.di.DaggerAppComponent

/**
 * Created by Stu Stirling on 06/08/2017.
 */
class MovieDbApplication : Application() {

    public val appComponent : AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule( AppModule(applicationContext))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}