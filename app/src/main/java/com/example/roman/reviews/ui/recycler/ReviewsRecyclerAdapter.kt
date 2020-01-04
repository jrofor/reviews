package com.example.roman.reviews.ui.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.roman.reviews.R
import com.example.roman.reviews.data.remote.models.dto.ReviewDTO

class ReviewsRecyclerAdapter(context: Context) : RecyclerView.Adapter<CustomViewHolder>() {
    private val layoutItem = R.layout.item_reviews
    private var reviews: MutableList<ReviewDTO> = mutableListOf()
    private val imageOption: RequestOptions = RequestOptions()
        .placeholder(R.drawable.image_placeholder)
        .fallback(R.drawable.image_placeholder)
        .centerCrop()
    private val glideRequestManager: RequestManager =
        Glide.with(context).applyDefaultRequestOptions(imageOption)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layoutItem, parent, false)
        return CustomViewHolder(view, glideRequestManager)
    }

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val reviewDTO = reviews[position]
        holder.bindTo(reviewDTO)
    }

    fun replaceItems(newReviews: List<ReviewDTO>) {
        this.reviews.clear()
        this.reviews.addAll(newReviews)
        notifyDataSetChanged()
    }

    fun getItems(position: Int): ReviewDTO {
        return reviews[position]
    }


}