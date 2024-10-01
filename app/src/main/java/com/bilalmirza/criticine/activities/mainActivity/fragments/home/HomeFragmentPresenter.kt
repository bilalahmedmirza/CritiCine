package com.bilalmirza.criticine.activities.mainActivity.fragments.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.bilalmirza.criticine.api.RetrofitInstance
import com.bilalmirza.criticine.model.movieShow.MovieShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentPresenter(private val view: HomeFragmentView) {
    fun getMostPopularMovies(context: Context) {
        RetrofitInstance.api.getMostPopularMovies()
            .enqueue(object : Callback<MovieShowResponse> {
                override fun onResponse(
                    call: Call<MovieShowResponse>,
                    response: Response<MovieShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val movies = response.body()
                        if (movies != null) {
                            Log.d("getMostPopularMovies", "Great Success!")
                            view.onMostPopularMovies(movies.results!!)
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

    fun getComingSoonImages(context: Context) {
        RetrofitInstance.api.getComingSoonImages()
            .enqueue(object : Callback<MovieShowResponse> {
                override fun onResponse(
                    call: Call<MovieShowResponse>,
                    response: Response<MovieShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val comingSoonResponse = response.body()
                        if (comingSoonResponse != null) {
                            view.onComingSoonImages(comingSoonResponse.results!!)
                            Log.d("getComingSoonImages", "Great Success!")
                        }
                    }
                }

                override fun onFailure(call: Call<MovieShowResponse>, t: Throwable) {
                    Toast.makeText(
                        context, "Error while displaying Coming Soon films.", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("getComingSoonImages", "getComingSoonImages: ${t.localizedMessage}")
                }
            })
    }

    fun getPlayingNowFilms(context: Context) {
        RetrofitInstance.api.getPlayingNowFilms()
            .enqueue(object : Callback<MovieShowResponse> {
                override fun onResponse(
                    call: Call<MovieShowResponse>,
                    response: Response<MovieShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val playingNowResponse = response.body()
                        if (playingNowResponse != null) {
                            view.onPlayingNowMovies(playingNowResponse.results!!)
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

    fun getBestSeries(context: Context) {
        RetrofitInstance.api.getBestSeries().enqueue(object : Callback<MovieShowResponse> {
            override fun onResponse(
                call: Call<MovieShowResponse>, response: Response<MovieShowResponse>
            ) {
                if (response.isSuccessful) {
                    val bestSeriesResponse = response.body()
                    if (bestSeriesResponse != null) {
                        Log.d("getBestSeries", "Great Success!")
                        view.onBestSeries(bestSeriesResponse.results!!)
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