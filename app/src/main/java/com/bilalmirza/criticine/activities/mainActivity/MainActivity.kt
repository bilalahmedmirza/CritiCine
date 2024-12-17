package com.bilalmirza.criticine.activities.mainActivity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bilalmirza.criticine.databinding.ActivityMainBinding
import com.google.firebase.Firebase
//import com.google.firebase.appdistribution.FirebaseAppDistribution
//import com.google.firebase.appdistribution.InterruptionLevel
//import com.google.firebase.appdistribution.appDistribution

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: MainActivityViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  showing the notification for testers to give feedback
//        Firebase.appDistribution.showFeedbackNotification(
//            "Collecting feedback. Tap to register feedback.", InterruptionLevel.HIGH
//        )
        //  check if new releases are available
//        checkForUpdates()

        //  initialising the view pager
        initViewPager()
        //  bottom nav bar click listeners
        bottomNavBarClickListeners()
    }

//    override fun onStart() {
//        super.onStart()
//        if (!Firebase.appDistribution.isTesterSignedIn) {
//            Firebase.appDistribution.signInTester()
//        }
//    }

//    private fun checkForUpdates() {
//        FirebaseAppDistribution.getInstance().checkForNewRelease().addOnSuccessListener {
//            if (it != null) {
//                Toast.makeText(
//                    this,
//                    "New build available.\n Please update before using the app.",
//                    Toast.LENGTH_SHORT
//                ).show()
//                Firebase.appDistribution.updateIfNewReleaseAvailable()
//            } else {
//                Toast.makeText(this, "No new build available.", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener {
//            Log.d("TAG789456123", "checkForUpdates: ${it.localizedMessage}")
//        }
//    }

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