package com.example.roman.reviews.ui.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.roman.reviews.R
import com.example.roman.reviews.data.remote.models.dto.ReviewDTO

class CustomViewHolder(itemView: View, glideRequestManager: RequestManager) :
    RecyclerView.ViewHolder(itemView) {
    init {
        findViews(itemView)
    }

    private lateinit var tvDisplayTitle: TextView
    private lateinit var tvMpaaRating: TextView
    private lateinit var tvPublicationDate: TextView
    private lateinit var tvHeadline: TextView
    private lateinit var tvSummaryShort: TextView
    private lateinit var ivMultimedia: ImageView
    var imageLoader: RequestManager = glideRequestManager

    private fun findViews(itemView: View) {
        tvDisplayTitle = itemView.findViewById(R.id.tv_display_title)
        tvMpaaRating = itemView.findViewById(R.id.tv_mpaa_rating)
        tvPublicationDate = itemView.findViewById(R.id.tv_publication_date)
        tvHeadline = itemView.findViewById(R.id.tv_headline)
        tvSummaryShort = itemView.findViewById(R.id.tv_summary_short)
        ivMultimedia = itemView.findViewById(R.id.iv_multimedia)
    }

    fun bindTo(item: ReviewDTO) {
        setupUi(item)
    }

    private fun setupUi(item: ReviewDTO) {
        tvDisplayTitle.text = item.display_title
        tvMpaaRating.text = item.mpaa_rating
        tvPublicationDate.text = item.publication_date
        tvHeadline.text = item.headline
        tvSummaryShort.text = item.summary_short
        imageLoader.load(item.multimedia.src)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
            .thumbnail(0.3f)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivMultimedia)
    }
}