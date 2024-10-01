package com.bilalmirza.criticine.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.bilalmirza.criticine.R
import com.bilalmirza.criticine.activities.logIn.LoginScreenActivity
import com.bilalmirza.criticine.activities.mainActivity.MainActivity
import com.bilalmirza.criticine.databinding.ActivitySplashBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_CritiCine)
        setContentView(binding.root)

        //  changing color of status bar text programmatically
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        //  navigating from splash screen to login screen
        splashToLogin()
    }

    private fun splashToLogin() {
        Handler(mainLooper).postDelayed({
            val currentUser = Firebase.auth.currentUser
            if (currentUser == null) {
                Intent(this@SplashActivity, LoginScreenActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            } else {
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }, 500)
    }
}