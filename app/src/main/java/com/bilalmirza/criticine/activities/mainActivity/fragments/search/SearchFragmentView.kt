package com.bilalmirza.criticine.activities.mainActivity.fragments.search

import com.bilalmirza.criticine.model.movieShow.MovieShowResult

interface SearchFragmentView {
    fun onSearchFilm(searchResult: List<MovieShowResult>, currentPage: Int, lastPage: Int)
    fun onSearchShow(searchResult: List<MovieShowResult>, currentPage: Int, lastPage: Int)
    fun onError(msg: String)
    fun loadProgressBar(isForPagination: Boolean)
    fun hideLoader()
}