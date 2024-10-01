package com.bilalmirza.criticine.activities.movieDetail.fragments.reviews

import com.bilalmirza.criticine.model.reviews.ReviewsResult

interface ReviewsFragmentView {
    fun onReviewsSuccess(reviewsResult: List<ReviewsResult>, currentPage: Int, lastPage: Int)
    fun showLoader()
    fun hideLoader()
}