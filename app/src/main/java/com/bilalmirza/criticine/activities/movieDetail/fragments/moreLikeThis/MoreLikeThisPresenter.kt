package com.bilalmirza.criticine.activities.movieDetail.fragments.moreLikeThis

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.bilalmirza.criticine.api.RetrofitInstance
import com.bilalmirza.criticine.model.movieShow.MovieShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreLikeThisPresenter(private val view: MoreLikeThisView) {
    fun getMoreLikeThis(movieID: Int, context: Context) {
        RetrofitInstance.api.getSimilarFilms(movieID).enqueue(object : Callback<MovieShowResponse> {
            override fun onResponse(
                call: Call<MovieShowResponse>, response: Response<MovieShowResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()
                    if (movies != null) {
                        Log.d("getMoreLikeThis", "getMoreLikeThis: Great Success!")
                        view.onGetMoreFilmsLikeThis(movies.results!!)
                    }
                }
            }

            override fun onFailure(call: Call<MovieShowResponse>, t: Throwable) {
                Toast.makeText(context, "Error while getting similar films.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun getShowsLikeThis(seriesID: Int, context: Context) {
        RetrofitInstance.api.getSimilarShows(seriesID).enqueue(object : Callback<MovieShowResponse> {
            override fun onResponse(
                call: Call<MovieShowResponse>, response: Response<MovieShowResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()
                    if (movies != null) {
                        Log.d("getMoreLikeThis", "getMoreLikeThis: Great Success!")
                        view.onGetMoreShowsLikeThis(movies.results!!)
                    }
                }
            }

            override fun onFailure(call: Call<MovieShowResponse>, t: Throwable) {
                Toast.makeText(context, "Error while getting similar films.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}