package com.bilalmirza.criticine.model.trailer

import com.google.gson.annotations.SerializedName

class TrailerResult {
    val id: String = ""

    @SerializedName("iso_3166_1")
    val iso31661: String = ""

    @SerializedName("iso_639_1")
    val iso6391: String = ""
    val key: String = ""
    val name: String = ""
    val official: Boolean = false

    @SerializedName("published_at")
    val publishedAt: String = ""
    val site: String = ""
    val size: Int = 0
    val type: String = ""
}