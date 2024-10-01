package com.bilalmirza.criticine.activities.movieDetail.fragments.reviews

import android.content.Context
import android.widget.Toast
import com.bilalmirza.criticine.api.RetrofitInstance
import com.bilalmirza.criticine.api.RetrofitSentiment
import com.bilalmirza.criticine.model.reviews.MovieShowReviewsResponse
import com.bilalmirza.criticine.model.reviews.ReviewsResult
import com.bilalmirza.criticine.model.sentimentAnalysis.Document
import com.bilalmirza.criticine.model.sentimentAnalysis.SentimentRequest
import com.bilalmirza.criticine.model.sentimentAnalysis.SentimentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsFragmentPresenter(private val view: ReviewsFragmentView) {
    private var reviewsAfterSentiment = ArrayList<ReviewsResult>()

    fun getReviews(id: Int, context: Context, page: Int, isSeries: Boolean) {
        if (isSeries) {
            view.showLoader()
            RetrofitInstance.api.getSeriesReviews(id, page)
                .enqueue(object : Callback<MovieShowReviewsResponse> {
                    override fun onResponse(
                        call: Call<MovieShowReviewsResponse>,
                        response: Response<MovieShowReviewsResponse>
                    ) {
                        if (response.isSuccessful) {
                            reviewsAfterSentiment.clear()
                            analyzeSentiments(
                                context = context,
                                review = ArrayList(response.body()?.results ?: emptyList()),
                                currentPage = response.body()?.page!!,
                                lastPage = response.body()?.totalPages!!
                            )
                        }
                        view.hideLoader()
                    }

                    override fun onFailure(call: Call<MovieShowReviewsResponse>, t: Throwable) {
                        Toast.makeText(
                            context, "Error while displaying reviews.", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        } else {
            view.showLoader()
            RetrofitInstance.api.getFilmReviews(id, page)
                .enqueue(object : Callback<MovieShowReviewsResponse> {
                    override fun onResponse(
                        call: Call<MovieShowReviewsResponse>,
                        response: Response<MovieShowReviewsResponse>
                    ) {
                        if (response.isSuccessful) {
                            reviewsAfterSentiment.clear()
                            analyzeSentiments(
                                context = context,
                                review = ArrayList(response.body()?.results ?: emptyList()),
                                currentPage = response.body()?.page!!,
                                lastPage = response.body()?.totalPages!!
                            )
                        }
                        view.hideLoader()
                    }

                    override fun onFailure(call: Call<MovieShowReviewsResponse>, t: Throwable) {
                        Toast.makeText(
                            context, "Error while displaying reviews.", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }


    fun analyzeSentiments(
        context: Context, review: ArrayList<ReviewsResult>, currentPage: Int, lastPage: Int
    ) {
        if (review.isEmpty()) {
            view.onReviewsSuccess(
                reviewsAfterSentiment, currentPage, lastPage
            )
            return
        }

        val reviewToAnalyze = review.first()
        val request = SentimentRequest(
            document = Document(
                type = "PLAIN_TEXT", content = reviewToAnalyze.content
            )
        )
        RetrofitSentiment.apiAnalysis.analyzeSentiment(request)
            .enqueue(object : Callback<SentimentResponse> {
                override fun onResponse(
                    call: Call<SentimentResponse>, response: Response<SentimentResponse>
                ) {
                    val sentimentScore = response.body()?.documentSentiment?.score
                    reviewToAnalyze.sentimentScore = sentimentScore
                    reviewsAfterSentiment.add(reviewToAnalyze)
                    review.removeAt(0)
                    analyzeSentiments(context, review, currentPage, lastPage)
                }

                override fun onFailure(call: Call<SentimentResponse>, t: Throwable) {
                    reviewToAnalyze.sentimentScore = 0f
                    reviewsAfterSentiment.add(reviewToAnalyze)
                    review.removeAt(0)
                    analyzeSentiments(context, review, currentPage, lastPage)
                }
            })
    }
}