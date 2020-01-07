package com.example.roman.reviews.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roman.reviews.R
import com.example.roman.reviews.data.remote.RestApi
import com.example.roman.reviews.data.remote.models.dto.MovieReviewsResponse
import com.example.roman.reviews.data.remote.models.dto.ReviewDTO
import com.example.roman.reviews.ui.recycler.ReviewsRecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RevListFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var error: View
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var reviewsAdapter: ReviewsRecyclerAdapter
    private val service = RestApi.getInstance().movieReviews()
    private val call = service.getMovieReviews()
    private lateinit var movieReviewsResponse: List<ReviewDTO>

    companion object {
        private const val LAYOUT = R.layout.fragment_rev_list
        private const val TAG = "myLogs"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(LAYOUT, container, false)
        setupUi(view)
        loadItems()
        Log.d(TAG, "--- RevListFragment onCreateView")
        return view
    }

    private fun setupUi(view: View) {
        findViews(view)
        setupRecyclerViewAdapter()
    }

    private fun findViews(view: View) {
        recycler = view.findViewById(R.id.rv_reviews)
        error = view.findViewById(R.id.ll_error)
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout)
    }

    private fun setupRecyclerViewAdapter() {
        reviewsAdapter = ReviewsRecyclerAdapter(activity!!)
        recycler.adapter = reviewsAdapter
        recycler.layoutManager = LinearLayoutManager(activity)
        reviewsAdapter.onItemClick = { contact -> openUrl(contact) }
    }

    private fun openUrl(url: String) {
        val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (urlIntent.resolveActivity(activity!!.packageManager) == null) {
            Snackbar.make(recycler, "problem", Snackbar.LENGTH_SHORT).show()
            return
        }
        startActivity(urlIntent)
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

    fun loadItems(searchQuery: String) {
        service.getSearchingMovieReviews(searchQuery).clone()
            .enqueue(object : Callback<MovieReviewsResponse> {
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