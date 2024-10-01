package com.bilalmirza.criticine.activities.mainActivity.fragments.home

import com.bilalmirza.criticine.model.movieShow.MovieShowResult

interface HomeFragmentView {
    fun onMostPopularMovies(mostPopularResponse: List<MovieShowResult>)
    fun onComingSoonImages(comingSoonImages: List<MovieShowResult>)
    fun onPlayingNowMovies(playingNowMoviesResponse: List<MovieShowResult>)
    fun onBestSeries(bestSeries: List<MovieShowResult>)
}