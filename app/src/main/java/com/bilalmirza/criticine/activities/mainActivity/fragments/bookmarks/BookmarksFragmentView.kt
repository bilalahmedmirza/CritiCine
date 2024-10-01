package com.bilalmirza.criticine.activities.mainActivity.fragments.bookmarks

import com.bilalmirza.criticine.model.movieShow.MovieShowResult

interface BookmarksFragmentView {
    fun onSuccessGet(movieShowResultList: List<MovieShowResult>)
}