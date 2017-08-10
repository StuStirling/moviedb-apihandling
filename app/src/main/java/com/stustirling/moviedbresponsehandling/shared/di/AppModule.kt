package com.stustirling.moviedbresponsehandling.shared.di

import android.content.Context
import com.stustirling.moviedbresponsehandling.shared.api.MovieDbApi
import com.stustirling.moviedbresponsehandling.shared.di.scopes.ForApplication
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Stu Stirling on 06/08/2017.
 */
@Module
@Singleton
class AppModule(val appContext: Context) {

    @Provides
    @Singleton
    @ForApplication
    fun providesApplicationContext() : Context {
        return appContext
    }

    @Provides
    @Singleton
    fun providesMovieDbApiService() : MovieDbApi {

        val apiKeyInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url()
            val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("api_key","0a08e38b874d0aa2d426ffc04357069d")
                    .build()

            return@Interceptor chain.proceed(
                    originalRequest.newBuilder()
                            .url(newUrl)
                            .build())
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()


        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.themoviedb.org/3/")
                .client(client)
                .build()

        return retrofit.create(MovieDbApi::class.java)
    }

}