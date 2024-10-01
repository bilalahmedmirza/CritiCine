package com.bilalmirza.criticine.model.movieShow

import com.google.gson.annotations.SerializedName

class MovieShowResponse {
    val page: Int? = null
    val results: List<MovieShowResult>? = null

    @SerializedName("total_pages")
    val totalPages: Int? = null

    @SerializedName("total_results")
    val totalResults: Int? = null
}