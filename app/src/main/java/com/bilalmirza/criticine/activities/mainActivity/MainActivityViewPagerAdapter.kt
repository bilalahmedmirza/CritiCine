package com.bilalmirza.criticine.activities.mainActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bilalmirza.criticine.activities.mainActivity.fragments.bookmarks.BookmarksFragment
import com.bilalmirza.criticine.activities.mainActivity.fragments.home.HomeFragment
import com.bilalmirza.criticine.activities.mainActivity.fragments.profile.ProfileFragment
import com.bilalmirza.criticine.activities.mainActivity.fragments.search.SearchFragment

class MainActivityViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }

            1 -> {
                SearchFragment()
            }

            2 -> {
                BookmarksFragment()
            }

            else -> {
                ProfileFragment()
            }
        }
    }
}