package com.bilalmirza.criticine.activities.movieDetail

import com.bilalmirza.criticine.model.movieShow.MovieShowResult

interface MovieDetailView {
    fun onMovieDetail(responseMoviesResult: MovieShowResult)
    fun onSeriesDetail(responseSeriesResult: MovieShowResult)
    fun onSuccessSave(msg: String)
}