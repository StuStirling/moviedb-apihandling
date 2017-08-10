package com.stustirling.moviedbresponsehandling.shared.api

import com.stustirling.moviedbresponsehandling.shared.api.responses.PopularMoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbApi {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int?): Observable<PopularMoviesResponse>

}

