package com.bilalmirza.criticine.activities.movieDetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bilalmirza.criticine.activities.movieDetail.fragments.moreLikeThis.MoreLikeThisFragment
import com.bilalmirza.criticine.activities.movieDetail.fragments.reviews.ReviewsFragment
import com.bilalmirza.criticine.activities.movieDetail.fragments.trailer.TrailerFragment

class MovieDetailViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                TrailerFragment()
            }

            1 -> {
                ReviewsFragment()
            }

            else -> {
                MoreLikeThisFragment()
            }
        }
    }
}