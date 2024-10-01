package com.bilalmirza.criticine.activities.movieDetail

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.bilalmirza.criticine.api.RetrofitInstance
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailPresenter(private val view: MovieDetailView) {
    fun getDetailsByID(movieID: Int, context: Context) {
        RetrofitInstance.api.getDetailsByID(movieID).enqueue(object : Callback<MovieShowResult> {
            override fun onResponse(
                call: Call<MovieShowResult>, response: Response<MovieShowResult>
            ) {
                if (response.isSuccessful) {
                    val movieDetail = response.body()
                    if (movieDetail != null) {
                        Log.d("getDetailsByID", "getDetailsByID: Great Success!")
                        view.onMovieDetail(movieDetail)
                    }
                }
            }

            override fun onFailure(call: Call<MovieShowResult>, t: Throwable) {
                Toast.makeText(context, "Error while getting details.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getSeriesDetails(seriesID: Int, context: Context) {
        RetrofitInstance.api.getSeriesDetails(seriesID).enqueue(object : Callback<MovieShowResult> {
            override fun onResponse(
                call: Call<MovieShowResult>, response: Response<MovieShowResult>
            ) {
                if (response.isSuccessful) {
                    val seriesDetail = response.body()
                    if (seriesDetail != null) {
                        Log.d("getSeriesDetails", "getSeriesDetails: Great Success!")
                        view.onSeriesDetail(seriesDetail)
                    }
                }
            }

            override fun onFailure(call: Call<MovieShowResult>, t: Throwable) {
                Toast.makeText(context, "Error while getting details.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun saveFilm(
        context: Context,
        uUID: String,
        id: Int,
        posterPath: String,
        voteAverage: Double,
        overview: String,
        isSeries: Boolean,
        name: String
    ) {
        val savedData = mapOf(
            "uUID" to uUID,
            "id" to id,
            "name" to name,
            "posterPath" to posterPath,
            "voteAverage" to voteAverage,
            "overview" to overview,
            "isSeries" to isSeries
        )
        Firebase.firestore.collection("users").document(Firebase.auth.currentUser?.uid!!)
            .collection("saved").document(uUID).set(savedData).addOnSuccessListener {
                view.onSuccessSave("$name saved successfully.")
                Log.d("saveFilm", "saveFilm: Great Success!")
            }.addOnFailureListener {
                Toast.makeText(context, "Error while saving $name.", Toast.LENGTH_SHORT).show()
                Log.d("saveFilm", "saveFilm: ${it.localizedMessage}")
            }
    }
}