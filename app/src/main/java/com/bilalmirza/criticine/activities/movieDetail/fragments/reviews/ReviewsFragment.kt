package com.bilalmirza.criticine.activities.movieDetail.fragments.reviews

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilalmirza.criticine.R
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity.Companion.isSeries
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity.Companion.movieID
import com.bilalmirza.criticine.databinding.FragmentReviewsBinding
import com.bilalmirza.criticine.interfaces.PaginationListener
import com.bilalmirza.criticine.model.reviews.ReviewsResult

class ReviewsFragment : Fragment(R.layout.fragment_reviews), ReviewsFragmentView {
    private lateinit var binding: FragmentReviewsBinding
    private lateinit var myAdapter: ReviewsAdapter
    private lateinit var presenter: ReviewsFragmentPresenter
    private var currentPage = 1
    private var lastPage = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewsBinding.bind(view)

        //  setting up presenter
        presenter = ReviewsFragmentPresenter(this)
        //  setting up recycler view
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        myAdapter = ReviewsAdapter(object : PaginationListener {
            override fun onNextPage() {
                if (currentPage < lastPage) {
                    presenter.getReviews(movieID, requireContext(), currentPage + 1, isSeries)
                }
            }
        })
        binding.reviewsRV.adapter = myAdapter
        binding.reviewsRV.layoutManager = LinearLayoutManager(requireContext())

        presenter.getReviews(movieID, requireContext(), 1, isSeries)
    }

    @SuppressLint("SetTextI18n")
    override fun onReviewsSuccess(
        reviewsResult: List<ReviewsResult>, currentPage: Int, lastPage: Int
    ) {
        this.currentPage = currentPage
        this.lastPage = lastPage

        if (reviewsResult.isEmpty()) {
            binding.reviewsRV.isVisible = false
            binding.emptyTV.text = "No reviews available."
            binding.emptyTV.isVisible = true
        } else {
            binding.reviewsRV.isVisible = true
            binding.emptyTV.isVisible = false

            if (currentPage > 1) {
                myAdapter.addList(reviewsResult)
            } else {
                myAdapter.updateList(reviewsResult)
            }
        }
    }

    override fun showLoader() {
        binding.loader.isVisible = true
        binding.reviewsRV.isVisible = false
    }

    override fun hideLoader() {
        binding.loader.isVisible = false
        binding.reviewsRV.isVisible = true
    }
}