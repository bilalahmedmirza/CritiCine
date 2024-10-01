package com.bilalmirza.criticine.activities.movieDetail.fragments.moreLikeThis

import com.bilalmirza.criticine.model.movieShow.MovieShowResult

interface MoreLikeThisView {
    fun onGetMoreFilmsLikeThis(similarMovieResponse: List<MovieShowResult>)
    fun onGetMoreShowsLikeThis(similarMovieResponse: List<MovieShowResult>)
}