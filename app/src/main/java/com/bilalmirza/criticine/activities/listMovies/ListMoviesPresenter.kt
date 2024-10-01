package com.bilalmirza.criticine.activities.listMovies

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.bilalmirza.criticine.api.RetrofitInstance
import com.bilalmirza.criticine.model.movieShow.MovieShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMoviesPresenter(private val view: ListMoviesView) {

    fun getMostPopularMovies(context: Context, page: Int) {
        RetrofitInstance.api.getMostPopularMovies(page)
            .enqueue(object : Callback<MovieShowResponse> {
                override fun onResponse(
                    call: Call<MovieShowResponse>, response: Response<MovieShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val movies = response.body()
                        if (movies != null) {
                            Log.d("getMostPopularMovies", "Great Success!")
                            view.listPopularMovies(movies.results!!, movies.page!!, movies.totalPages!!)
                        }
                    }
                }

                override fun onFailure(call: Call<MovieShowResponse>, t: Throwable) {
                    Toast.makeText(
                        context, "Error while displaying Most Popular films.", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("getMostPopularMovies", "getMostPopularMovies: ${t.localizedMessage}")
                }
            })
    }

    fun getPlayingNowFilms(context: Context, page: Int) {
        RetrofitInstance.api.getPlayingNowFilms(page).enqueue(object : Callback<MovieShowResponse> {
            override fun onResponse(
                call: Call<MovieShowResponse>, response: Response<MovieShowResponse>
            ) {
                if (response.isSuccessful) {
                    val playingNowResponse = response.body()
                    if (playingNowResponse != null) {
                        view.listPlayingNow(
                            playingNowResponse.results!!,
                            playingNowResponse.page!!,
                            playingNowResponse.totalPages!!
                        )
                        Log.d("getPlayingNowFilms", "Great Success!")
                    }
                }
            }

            override fun onFailure(call: Call<MovieShowResponse>, t: Throwable) {
                Toast.makeText(
                    context, "Error while displaying Playing Now films.", Toast.LENGTH_SHORT
                ).show()
                Log.d("getPlayingNowFilms", "getPlayingNowFilms: ${t.localizedMessage}")
            }
        })
    }

    fun getBestSeries(context: Context, page: Int) {
        RetrofitInstance.api.getBestSeries(page).enqueue(object : Callback<MovieShowResponse> {
            override fun onResponse(
                call: Call<MovieShowResponse>, response: Response<MovieShowResponse>
            ) {
                if (response.isSuccessful) {
                    val bestSeriesResponse = response.body()
                    if (bestSeriesResponse != null) {
                        Log.d("getBestSeries", "Great Success!")
                        view.listBestSeries(
                            bestSeriesResponse.results!!,
                            bestSeriesResponse.page!!,
                            bestSeriesResponse.totalPages!!
                        )
                    }
                }
            }

            override fun onFailure(call: Call<MovieShowResponse>, t: Throwable) {
                Toast.makeText(
                    context, "Error while displaying Best Series.", Toast.LENGTH_SHORT
                ).show()
                Log.d("getBestSeries", "getPlayingNowFilms: ${t.localizedMessage}")
            }
        })
    }
}