package com.stustirling.moviedbresponsehandling.shared.api.entities

import java.util.*

/**
 * Created by Stu Stirling on 06/08/2017.
 */
data class MovieSummary(
        val id : Int,
        val title: String,
        val overview: String,
        val vote_average: Float,
        val release_date: Date,
        val poster_path: String,
        val genre_ids: IntArray)