package com.bilalmirza.criticine.activities.mainActivity.fragments.bookmarks

import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookmarksFragmentPresenter(private val view: BookmarksFragmentView) {
    fun getSavedShowMovies() {
        val movieShowList: ArrayList<MovieShowResult> = arrayListOf()
        Firebase.firestore.collection("users").document(Firebase.auth.currentUser?.uid!!)
            .collection("saved").addSnapshotListener { value, _ ->
                movieShowList.clear()
                value?.documents?.forEach {
                    val dataMap = it.data as HashMap<String, Any>
                    val id = dataMap["id"] as Long
                    val name = dataMap["name"] as String
                    val posterPath = dataMap["posterPath"] as String
                    val voteAverage = dataMap["voteAverage"] as Double
                    val overview = dataMap["overview"] as String
                    val isSeries = dataMap["isSeries"] as Boolean
                    val uUID = dataMap["uUID"] as String

                    val data = MovieShowResult(
                        id = id.toInt(),
                        name = name,
                        posterPath = posterPath,
                        voteAverage = voteAverage,
                        overview = overview,
                        isSeries = isSeries,
                        uUID = uUID
                    )
                    movieShowList.add(data)
                }
                view.onSuccessGet(movieShowList)
            }
    }

    fun deleteFilmShow(uuid: String) {
        Firebase.firestore.collection("users").document(Firebase.auth.currentUser?.uid!!)
            .collection("saved").document(uuid).delete()
    }
}