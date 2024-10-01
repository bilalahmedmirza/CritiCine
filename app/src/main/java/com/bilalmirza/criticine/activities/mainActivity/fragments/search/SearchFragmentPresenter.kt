package com.bilalmirza.criticine.activities.mainActivity.fragments.search

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.bilalmirza.criticine.api.RetrofitInstance
import com.bilalmirza.criticine.model.movieShow.MovieShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragmentPresenter(private val view: SearchFragmentView) {
    fun searchTitleByName(
        titleName: String,
        context: Context,
        movieSelected: Boolean,
        showSelected: Boolean,
        page: Int
    ) {
        if (titleName.isEmpty()) {
            view.onError("Please enter a title.")
        } else if (movieSelected) {
            view.loadProgressBar(page > 1)
            RetrofitInstance.api.searchMovieByName(titleName, page)
                .enqueue(object : Callback<MovieShowResponse> {
                    override fun onResponse(
                        call: Call<MovieShowResponse>, response: Response<MovieShowResponse>
                    ) {
                        if (response.isSuccessful) {
                            val searchResult = response.body()
                            if (searchResult != null) {
                                view.onSearchFilm(
                                    searchResult.results!!,
                                    searchResult.page!!,
                                    searchResult.totalPages!!
                                )
                                Log.d("searchTitleByName", "searchTitleByName: Great Success!")
                            }
                        }
                        view.hideLoader()
                    }

                    override fun onFailure(call: Call<MovieShowResponse>, t: Throwable) {
                        Toast.makeText(
                            context, "Error searching for $titleName", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        } else if (showSelected) {
            view.loadProgressBar(page > 1)
            RetrofitInstance.api.searchSeriesByName(titleName, page)
                .enqueue(object : Callback<MovieShowResponse> {
                    override fun onResponse(
                        call: Call<MovieShowResponse>, response: Response<MovieShowResponse>
                    ) {
                        if (response.isSuccessful) {
                            val searchResult = response.body()
                            if (searchResult != null) {
                                view.onSearchShow(
                                    searchResult.results!!,
                                    searchResult.page!!,
                                    searchResult.totalPages!!
                                )
                                Log.d("searchTitleByName", "searchTitleByName: Great Success!")
                            }
                        }
                        view.hideLoader()
                    }

                    override fun onFailure(call: Call<MovieShowResponse>, t: Throwable) {
                        Toast.makeText(
                            context, "Error searching for $titleName", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}