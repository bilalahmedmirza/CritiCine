package com.bilalmirza.criticine.model.reviews

import com.google.gson.annotations.SerializedName

class MovieShowReviewsResponse {
    val id: Int = 0
    val page: Int = 0
    val results: List<ReviewsResult> = emptyList()

    @SerializedName("total_pages")
    val totalPages: Int = 0

    @SerializedName("total_results")
    val totalResults: Int = 0
}