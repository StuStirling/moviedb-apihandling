package com.stustirling.moviedbresponsehandling.shared

/**
 * Created by Stu Stirling on 06/08/2017.
 */
class RetrofitResult<T>(data: T? = null, error: Throwable? = null) : Result<T>(data,error) {



}