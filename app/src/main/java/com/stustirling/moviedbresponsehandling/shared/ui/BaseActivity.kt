package com.stustirling.moviedbresponsehandling.shared.ui

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import com.stustirling.moviedbresponsehandling.MovieDbApplication
import com.stustirling.moviedbresponsehandling.shared.di.AppComponent



/**
 * Created by Stu Stirling on 06/08/2017.
 */
abstract class BaseActivity : LifecycleActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject((application as MovieDbApplication).appComponent)
        bindViewModel()
    }

    abstract fun bindViewModel()

    abstract fun inject(appComponent: AppComponent)
}