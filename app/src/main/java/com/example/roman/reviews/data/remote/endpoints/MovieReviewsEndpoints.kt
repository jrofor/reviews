package com.example.roman.reviews.data.remote.endpoints

import com.example.roman.reviews.data.remote.models.dto.MovieReviewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieReviewsEndpoints {

    @GET("svc/movies/v2/reviews/all.json")
    fun getMovieReviews(): Call<MovieReviewsResponse>

    @GET("svc/movies/v2/reviews/search.json")
    fun getSearchingMovieReviews(@Query("query") searchQuery: String): Call<MovieReviewsResponse>

}