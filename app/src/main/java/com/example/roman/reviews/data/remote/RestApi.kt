package com.example.roman.reviews.data.remote

import com.example.roman.reviews.data.remote.endpoints.MovieReviewsEndpoints
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestApi private constructor() {

    companion object {
        private var restApi: RestApi = RestApi()
        private const val MOVIE_REVIEWS_BASE_URL = "https://api.nytimes.com/"
        private const val API_KEY = "xTvrIaqsho3wZBAPUcJP7H8KqBl9cSaG"
        private const val TIMEOUT_IN_SECONDS = 2L

        @Synchronized
        fun getInstance(): RestApi {
            return this.restApi
        }
    }

    private val movieReviewsEndpoints: MovieReviewsEndpoints

    init {
        val client = buildClient()
        val retrofit = buildRetrofit(client)
        movieReviewsEndpoints = retrofit.create(MovieReviewsEndpoints::class.java)
    }

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MOVIE_REVIEWS_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun buildClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor.create(API_KEY))
            .connectTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    fun movieReviews(): MovieReviewsEndpoints {
        return movieReviewsEndpoints
    }
}


