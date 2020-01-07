package com.example.roman.reviews.data.remote.models.dto

import com.google.gson.annotations.SerializedName

data class ReviewDTO(
    @SerializedName("display_title")
    var display_title: String,
    @SerializedName("posterPath")
    var posterPath: String,
    @SerializedName("mpaa_rating")
    var mpaa_rating: String,
    @SerializedName("publication_date")
    var publication_date: String,
    @SerializedName("headline")
    var headline: String,
    @SerializedName("summary_short")
    var summary_short: String,
    var link: Link,
    var multimedia: MultiMedia?
)

data class MultiMedia(
    @SerializedName("src")
    val src: String?
)

data class Link(
    @SerializedName("url")
    val url: String
)
