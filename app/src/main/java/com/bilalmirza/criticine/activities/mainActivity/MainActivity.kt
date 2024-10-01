package com.bilalmirza.criticine.activities.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bilalmirza.criticine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: MainActivityViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  initialising the view pager
        initViewPager()
        //  bottom nav bar click listeners
        bottomNavBarClickListeners()
    }

    // function to change the icon color and view pager fragment
    private fun renderState(number: Int) {
        binding.viewPagerMainScreen.currentItem = number

        binding.rootHomeBtn.isSelected = number == 0

        binding.rootSearchBtn.isSelected = number == 1

        binding.rootBookmarkBtn.isSelected = number == 2

        binding.rootProfileBtn.isSelected = number == 3
    }

    private fun initViewPager() {
        viewPagerAdapter = MainActivityViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerMainScreen.adapter = viewPagerAdapter
        binding.viewPagerMainScreen.isUserInputEnabled = false
        renderState(0)
    }

    private fun bottomNavBarClickListeners() {
        binding.rootHomeBtn.setOnClickListener {
            renderState(0)
        }
        binding.rootSearchBtn.setOnClickListener {
            renderState(1)
        }
        binding.rootBookmarkBtn.setOnClickListener {
            renderState(2)
        }
        binding.rootProfileBtn.setOnClickListener {
            renderState(3)
        }
    }
}