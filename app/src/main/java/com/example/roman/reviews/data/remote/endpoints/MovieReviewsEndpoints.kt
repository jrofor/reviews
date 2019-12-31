package com.example.roman.reviews.data.remote.endpoints

import com.example.roman.reviews.data.remote.models.dto.MovieReviewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieReviewsEndpoints {

    @GET("svc/movies/v2/reviews/picks.json")
    fun getMovieReviews(): Call<MovieReviewsResponse>

}