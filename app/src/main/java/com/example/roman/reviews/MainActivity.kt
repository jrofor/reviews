package com.example.roman.reviews

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.roman.reviews.data.remote.RestApi
import com.example.roman.reviews.data.remote.models.dto.MovieReviewsResponse
import com.example.roman.reviews.data.remote.models.dto.ReviewDTO
import com.example.roman.reviews.ui.recycler.ReviewsRecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG = "myLogs"
    private lateinit var recycler: RecyclerView
    private lateinit var error: View
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var reviewsAdapter: ReviewsRecyclerAdapter
    private val service = RestApi.getInstance().movieReviews()
    private val call = service.getMovieReviews()
    private lateinit var movieReviewsResponse: List<ReviewDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()
        loadItems()
    }

    private fun setupUi() {
        findViews()
        setupRecyclerViewAdapter()
    }

    private fun findViews() {
        recycler = findViewById(R.id.rv_reviews)
        error = findViewById(R.id.ll_error)
        refreshLayout = findViewById(R.id.swipe_refresh_layout)
    }

    private fun setupRecyclerViewAdapter() {
        reviewsAdapter = ReviewsRecyclerAdapter(this)
        recycler.adapter = reviewsAdapter
        recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        refreshLayout.setOnRefreshListener {
            loadItems()
        }
    }

    private fun loadItems() {
        call.clone().enqueue(object : Callback<MovieReviewsResponse> {
            override fun onResponse(
                call: Call<MovieReviewsResponse>,
                response: Response<MovieReviewsResponse>
            ) {
                refreshLayout.isRefreshing = false
                if (response.code() == 200) {
                    Log.d(TAG, "------------")
                    movieReviewsResponse = response.body()!!.results
                    updateItems(movieReviewsResponse)
                }
            }

            override fun onFailure(call: Call<MovieReviewsResponse>, t: Throwable) {
                refreshLayout.isRefreshing = false
                error.visibility = View.VISIBLE
                recycler.visibility = View.GONE
            }
        })
    }

    private fun updateItems(movieReviews: List<ReviewDTO>) {
        error.visibility = View.GONE
        recycler.visibility = View.VISIBLE
        reviewsAdapter.replaceItems(movieReviews)
    }

}
