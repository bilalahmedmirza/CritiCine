package com.bilalmirza.criticine.activities.movieDetail.fragments.trailer

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.bilalmirza.criticine.api.RetrofitInstance
import com.bilalmirza.criticine.model.trailer.TrailerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrailerFragmentPresenter(private val view: TrailerFragmentView) {
    fun getFilmTrailer(id: Int, context: Context) {
        RetrofitInstance.api.getFilmTrailer(id).enqueue(object : Callback<TrailerResponse> {
            override fun onResponse(
                call: Call<TrailerResponse>, response: Response<TrailerResponse>
            ) {
                if (response.isSuccessful) {
                    val movieTrailer = response.body()
                    if (movieTrailer != null) {
                        Log.d("getFilmTrailer", "getFilmTrailer: Great Success!")
                        view.onTrailerSuccess(movieTrailer.results)
                    }
                }
            }

            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                Toast.makeText(
                    context, "Error while fetching the movie trailer.", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun getSeriesTrailer(seriesID: Int, context: Context) {
        RetrofitInstance.api.getSeriesTrailer(seriesID).enqueue(object : Callback<TrailerResponse> {
            override fun onResponse(
                call: Call<TrailerResponse>,
                response: Response<TrailerResponse>
            ) {
                if (response.isSuccessful) {
                    val seriesTrailer = response.body()
                    if (seriesTrailer != null) {
                        Log.d("getSeriesTrailer", "getSeriesTrailer: Great Success!")
                        view.onSeriesTrailerSuccess(seriesTrailer.results)
                    }
                }
            }

            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                Toast.makeText(context, "Error while getting trailers.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}