package com.stustirling.moviedbresponsehandling.shared.api.responses

import com.stustirling.moviedbresponsehandling.shared.api.entities.MovieSummary

/**
 * Created by Stu Stirling on 06/08/2017.
 */
data class PopularMoviesResponse (
        val results: List<MovieSummary> )