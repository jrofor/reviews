package com.example.roman.reviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.roman.reviews.data.remote.RestApi
import com.example.roman.reviews.data.remote.models.dto.MovieReviewsResponse
import com.example.roman.reviews.data.remote.models.dto.ReviewDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadItems()
    }

    override fun onStart() {
        super.onStart()
        loadItems()
    }

    private fun loadItems() {
        val service = RestApi.getInstance().movieReviews()
        val call = service.getMovieReviews()

        call.enqueue(object : Callback<MovieReviewsResponse> {
            override fun onResponse(
                call: Call<MovieReviewsResponse>,
                response: Response<MovieReviewsResponse>
            ) {
                if (response.code() == 200) {
                    val movieReviewsResponse = response.body()!!
                    val stringBuild: List<ReviewDTO> = movieReviewsResponse.results
                    Log.d(TAG, "------------")
                    Log.d(TAG, stringBuild.toString())
                }
            }

            override fun onFailure(call: Call<MovieReviewsResponse>, t: Throwable) {
                Log.d(TAG, "Failure")
            }
        })

    }

}
