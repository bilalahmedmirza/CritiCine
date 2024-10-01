package com.bilalmirza.criticine.model.reviews

import com.google.gson.annotations.SerializedName

class ReviewsResult {
    val author: String = ""

    @SerializedName("author_details")
    val authorDetails: AuthorDetails = AuthorDetails()

    val content: String = ""

    @SerializedName("created_at")
    val createdAt: String = ""

    val id: String = ""

    @SerializedName("updated_at")
    val updatedAt: String = ""

    val url: String = ""

    var sentimentScore: Float? = null
}