package com.bilalmirza.criticine.activities.movieDetail.fragments.trailer

import com.bilalmirza.criticine.model.trailer.TrailerResult

interface TrailerFragmentView {
    fun onTrailerSuccess(trailerResult: List<TrailerResult>)
    fun onSeriesTrailerSuccess(trailerResult: List<TrailerResult>)
}