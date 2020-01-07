package com.example.roman.reviews.ui.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.roman.reviews.R
import com.example.roman.reviews.data.remote.models.dto.ReviewDTO

class ReviewsRecyclerAdapter(context: Context) :
    RecyclerView.Adapter<ReviewsRecyclerAdapter.CustomViewHolder>() {
    private val layoutItem = R.layout.item_reviews
    private var reviews: MutableList<ReviewDTO> = mutableListOf()
    private val imageOption: RequestOptions = RequestOptions()
        .placeholder(R.drawable.image_placeholder)
        .fallback(R.drawable.image_placeholder)
        .centerCrop()
    private val glideRequestManager: RequestManager =
        Glide.with(context).applyDefaultRequestOptions(imageOption)
    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layoutItem, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindTo(reviews[position])
    }

    fun replaceItems(newReviews: List<ReviewDTO>) {
        this.reviews.clear()
        this.reviews.addAll(newReviews)
        notifyDataSetChanged()
    }

    inner class CustomViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var tvDisplayTitle: TextView
        private lateinit var tvMpaaRating: TextView
        private lateinit var tvPublicationDate: TextView
        private lateinit var tvHeadline: TextView
        private lateinit var tvSummaryShort: TextView
        private lateinit var ivMultimedia: ImageView
        private lateinit var btnItem: Button
        private var imageLoader: RequestManager = glideRequestManager

        init {
            findViews(itemView)
        }

        private fun findViews(itemView: View) {
            tvDisplayTitle = itemView.findViewById(R.id.tv_display_title)
            tvMpaaRating = itemView.findViewById(R.id.tv_mpaa_rating)
            tvPublicationDate = itemView.findViewById(R.id.tv_publication_date)
            tvHeadline = itemView.findViewById(R.id.tv_headline)
            tvSummaryShort = itemView.findViewById(R.id.tv_summary_short)
            ivMultimedia = itemView.findViewById(R.id.iv_multimedia)
            btnItem = itemView.findViewById(R.id.btn_read_more)
        }

        fun bindTo(item: ReviewDTO) {
            setupUi(item)
            setupUx(item)
        }

        private fun setupUx(item: ReviewDTO) {
            btnItem.setOnClickListener {
                onItemClick?.invoke(item.link.url)
            }
        }

        private fun setupUi(item: ReviewDTO) {
            tvDisplayTitle.text = item.display_title
            tvMpaaRating.text = item.mpaa_rating
            tvPublicationDate.text = item.publication_date
            tvHeadline.text = item.headline
            tvSummaryShort.text = item.summary_short
            if (item.multimedia != null) {
                imageLoader.load(item.multimedia!!.src)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .thumbnail(0.3f)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivMultimedia)
            } else imageLoader.load(R.drawable.image_placeholder)
                .into(ivMultimedia)
        }
    }

}