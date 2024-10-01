package com.bilalmirza.criticine.api

import com.bilalmirza.criticine.model.movieShow.MovieShowResponse
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bilalmirza.criticine.model.reviews.MovieShowReviewsResponse
import com.bilalmirza.criticine.model.sentimentAnalysis.SentimentRequest
import com.bilalmirza.criticine.model.sentimentAnalysis.SentimentResponse
import com.bilalmirza.criticine.model.trailer.TrailerResponse
import com.bilalmirza.criticine.utils.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {
    @GET("movie/top_rated")
    fun getMostPopularMovies(
        @Query("page") pageNumber: Int = 1, @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResponse>

    @GET("movie/upcoming")
    fun getComingSoonImages(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResponse>

    @GET("movie/now_playing")
    fun getPlayingNowFilms(
        @Query("page") pageNumber: Int = 1, @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResponse>

    @GET("tv/top_rated")
    fun getBestSeries(
        @Query("page") pageNumber: Int = 1, @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResponse>

    @GET("movie/{movie_id}")
    fun getDetailsByID(
        @Path("movie_id") movieID: Int, @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResult>

    @GET("movie/{movie_id}/videos")
    fun getFilmTrailer(
        @Path("movie_id") movieID: Int, @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<TrailerResponse>

    @GET("tv/{series_id}/videos")
    fun getSeriesTrailer(
        @Path("series_id") seriesID: Int, @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<TrailerResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getSimilarFilms(
        @Path("movie_id") movieID: Int, @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResponse>

    @GET("tv/{series_id}/recommendations")
    fun getSimilarShows(
        @Path("series_id") seriesID: Int, @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResponse>

    @GET("movie/{movie_id}/reviews")
    fun getFilmReviews(
        @Path("movie_id") movieID: Int,
        @Query("page") pageNumber: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowReviewsResponse>

    @GET("tv/{series_id}/reviews")
    fun getSeriesReviews(
        @Path("series_id") seriesID: Int,
        @Query("page") pageNumber: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowReviewsResponse>

    @GET("tv/{series_id}")
    fun getSeriesDetails(
        @Path("series_id") seriesID: Int, @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResult>

    @GET("search/movie")
    fun searchMovieByName(
        @Query("query") movieName: String,
        @Query("page") pageNumber: Int = 1,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResponse>

    @GET("search/tv")
    fun searchSeriesByName(
        @Query("query") movieName: String,
        @Query("page") pageNumber: Int = 1,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<MovieShowResponse>

    @POST("v2/documents:analyzeSentiment")
    fun analyzeSentiment(
        @Body request: SentimentRequest,
        @Query("key") apiKey: String = Constants.SENTIMENT_ANALYSIS_API_KEY
    ): Call<SentimentResponse>
}