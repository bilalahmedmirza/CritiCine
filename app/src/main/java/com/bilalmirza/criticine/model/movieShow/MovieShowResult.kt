package com.bilalmirza.criticine.model.movieShow

import com.bilalmirza.criticine.model.genre.Genre
import com.google.gson.annotations.SerializedName

data class MovieShowResult(
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any? = null,

    val budget: Int? = null,

    val genres: List<Genre>? = null,

    val homepage: String? = null,

    @SerializedName("imdb_id")
    val imdbID: String? = null,

    @SerializedName("origin_country")
    val originCountry: List<String>? = null,
    val revenue: Long? = null,
    val status: String? = null,
    val tagline: String? = null,
    val adult: Boolean? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String = "",

    @SerializedName("genre_ids")
    val genreIDs: List<Int> = arrayListOf(),
    val id: Int = 0,

    @SerializedName("original_language")
    val originalLanguage: String = "",

    @SerializedName("original_title")
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val runtime: Int = 0,

    @SerializedName("poster_path")
    val posterPath: String = "",

    @SerializedName("release_date")
    val releaseDate: String = "",
    val title: String? = null,
    val name: String? = null,
    val video: Boolean = false,

    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,

    @SerializedName("vote_count")
    val voteCount: Int = 0,
    var isSeries: Boolean = false,
    var uUID: String = "",
)