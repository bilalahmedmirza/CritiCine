package com.bilalmirza.criticine.activities.listMovies

import com.bilalmirza.criticine.model.movieShow.MovieShowResult

interface ListMoviesView {
    fun listPopularMovies(
        mostPopularMoviesResponse: List<MovieShowResult>, currentPage: Int, lastPage: Int
    )

    fun listPlayingNow(
        playingNowMoviesResponse: List<MovieShowResult>, currentPage: Int, lastPage: Int
    )

    fun listBestSeries(
        bestSeriesResult: List<MovieShowResult>, currentPage: Int, lastPage: Int
    )
}