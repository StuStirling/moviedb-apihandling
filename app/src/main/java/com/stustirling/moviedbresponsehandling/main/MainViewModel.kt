package com.stustirling.moviedbresponsehandling.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.stustirling.moviedbresponsehandling.shared.*
import com.stustirling.moviedbresponsehandling.shared.api.MovieDbApi
import com.stustirling.moviedbresponsehandling.shared.api.entities.MovieSummary
import com.stustirling.moviedbresponsehandling.shared.di.scopes.ForActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Stu Stirling on 06/08/2017.
 */
@ForActivity
class MainViewModel(val movieDbApi: MovieDbApi) : ViewModel() {

    private val refreshSubject: PublishSubject<Unit> = PublishSubject.create()

    private val basicApiCall: ConnectableObservable<Result<List<MovieSummary>>>
    private val compositeDisposable = CompositeDisposable()

    init {
        basicApiCall = refreshSubject
                .flatMap { return@flatMap movieDbApi.getPopularMovies(1)
                        .map { it.results }
                        .toResult()
                        .subscribeOn(Schedulers.io())}
                .publish()

        compositeDisposable.add(basicApiCall.connect())
    }

    fun getPopularMoviesRetrievedObservable() : Observable<List<MovieSummary>> {
        return basicApiCall.onlySuccess()
    }

    fun getPopularMoviesPageErrorObservable() : Observable<Unit> {
        return basicApiCall.onlyHttpException(424)
                .map { Unit }
    }

    fun getPopularMoviesGenericErrorObservable() : Observable<Unit> {
        return basicApiCall.onlyHttpExceptionExcluding(424)
                .map { Unit }
    }


    fun getPopularMoviesRefreshObserver() : Observer<Unit> {
        return refreshSubject
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    class Factory @Inject constructor(val api : MovieDbApi ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(api) as T
        }
    }

}
