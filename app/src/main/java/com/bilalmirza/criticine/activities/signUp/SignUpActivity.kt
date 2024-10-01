package com.bilalmirza.criticine.activities.signUp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.bilalmirza.criticine.activities.logIn.SocialLoginActivityPresenter
import com.bilalmirza.criticine.activities.logIn.SocialLoginActivityView
import com.bilalmirza.criticine.activities.mainActivity.MainActivity
import com.bilalmirza.criticine.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity(), SignUpView, SocialLoginActivityView {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var myPresenter: SignUpPresenter
    private lateinit var socialLoginPresenter: SocialLoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  initialising the presenters
        myPresenter = SignUpPresenter(this)
        socialLoginPresenter = SocialLoginActivityPresenter(this)
        //  initializing the firebase auth
        auth = Firebase.auth
        //  changing color of status bar text programmatically
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        //  closing the keyboard and stopping the cursor from blinking
        closeKeyboardAndCursor()
        //  setting up click listeners
        clickListener()
    }

    private fun clickListener() {
        binding.signUpBtn.setOnClickListener {
            val name = binding.signUpUsernameET.text.toString()
            val email = binding.signUpEmailET.text.toString()
            val password = binding.signUpPasswordET.text.toString()
            val confirmPassword = binding.signUpConfirmPasswordET.text.toString()
            clearHelperTexts()

            myPresenter.signUp(name, email, password, confirmPassword)
        }
        binding.googleIcon.setOnClickListener {
            socialLoginPresenter.googleLogIn(this)
        }
    }

    private fun closeKeyboardAndCursor() {
        binding.signUpConfirmPasswordET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val view: View? = this.currentFocus
                if (view != null) {
                    val inputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                }
                binding.signUpConfirmPasswordET.clearFocus()
            }
            true
        }
    }

    override fun onSignupSuccess(msg: String) {
        Toast.makeText(this, "SignUp: $msg", Toast.LENGTH_SHORT).show()

        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun onSignupFailure(msg: String) {
        Toast.makeText(this, "SignUp: $msg\nPlease try again.", Toast.LENGTH_SHORT).show()
    }

    private fun clearHelperTexts() {
        binding.signUpUsernameLayout.helperText = ""
        binding.signUpEmailLayout.helperText = ""
        binding.signUpPasswordLayout.helperText = ""
        binding.signUpConfirmPasswordLayout.helperText = ""
    }

    override fun onShowError(msg: String, whichType: Int) {
        when (whichType) {
            0 -> binding.signUpUsernameLayout.helperText = msg
            1 -> binding.signUpEmailLayout.helperText = msg
            2 -> binding.signUpPasswordLayout.helperText = msg
            3 -> binding.signUpConfirmPasswordLayout.helperText = msg
            4 -> binding.signUpUsernameLayout.helperText = msg
            5 -> binding.signUpPasswordLayout.helperText = msg
        }
    }

    override fun onSocialSignUpSuccess() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}