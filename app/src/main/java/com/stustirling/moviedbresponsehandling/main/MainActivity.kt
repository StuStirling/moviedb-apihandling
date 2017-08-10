package com.stustirling.moviedbresponsehandling.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.stustirling.moviedbresponsehandling.R
import com.stustirling.moviedbresponsehandling.shared.di.AppComponent
import com.stustirling.moviedbresponsehandling.shared.di.scopes.ForActivity
import com.stustirling.moviedbresponsehandling.shared.ui.BaseActivity
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by Stu Stirling on 06/08/2017.
 */
class MainActivity : BaseActivity() {

    @Inject lateinit var factory: MainViewModel.Factory

    private val viewModel by lazy {
        ViewModelProviders.of(this,factory)
                .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_ma_popular_movies.setOnClickListener {
            viewModel.getPopularMoviesRefreshObserver().onNext(Unit)
        }
    }

    override fun inject(appComponent: AppComponent) {
        DaggerMainActivity_Component.builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun bindViewModel() {
        viewModel.getPopularMoviesRetrievedObservable()
                .bindToLifecycle(this@MainActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it.forEach { Log.d("MainActivity","Movie: $it") } }
        viewModel.getPopularMoviesPageErrorObservable()
                .bindToLifecycle(this@MainActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.d("MainActivity","Page of 0 error") }
        viewModel.getPopularMoviesGenericErrorObservable()
                .bindToLifecycle(this@MainActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.d("MainActivity","Something went wrong") }
    }

    @dagger.Component (
            dependencies = arrayOf(AppComponent::class)
    )
    @ForActivity
    interface Component {
        fun inject(activity: MainActivity)
    }


}