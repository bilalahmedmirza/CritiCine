package com.bilalmirza.criticine.model.reviews

import com.google.gson.annotations.SerializedName

class AuthorDetails {
    @SerializedName("avatar_path")
    val avatarPath: String = ""
    val name: String = ""
    val rating: Double = 0.0
    val username: String = ""
}