package com.bilalmirza.criticine.activities.logIn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bilalmirza.criticine.activities.forgotPassword.ForgotPasswordActivity
import com.bilalmirza.criticine.activities.mainActivity.MainActivity
import com.bilalmirza.criticine.activities.signUp.SignUpActivity
import com.bilalmirza.criticine.databinding.ActivityLoginScreenBinding

class LoginScreenActivity : AppCompatActivity(), LogInView, SocialLoginActivityView {
    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var myPresenter: LogInPresenter
    private lateinit var socialPresenter: SocialLoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  initialising the presenter
        myPresenter = LogInPresenter(this)
        //  initialising the social log in presenter
        socialPresenter = SocialLoginActivityPresenter(this)
        //  closing the keyboard and stopping the cursor from blinking
        closeKeyboardAndCursor()
        //  setting up click listeners
        clickListener()
    }

    private fun clickListener() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            clearHelperTexts()

            myPresenter.logIn(email, password)
        }
        binding.forgotPasswordTV.setOnClickListener {
            Intent(this, ForgotPasswordActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.signUpTV.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.googleIcon.setOnClickListener {
            socialPresenter.googleLogIn(this)
        }
    }

    override fun onLogInSuccess(msg: String) {
        Toast.makeText(this, "LogIn: $msg", Toast.LENGTH_SHORT).show()

        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun onLogInFailure(msg: String) {
        Toast.makeText(this, "LogIn: $msg\nPlease try again.", Toast.LENGTH_SHORT).show()
    }

    override fun onShowError(msg: String, whichType: Int) {
        when (whichType) {
            0 -> binding.emailLayout.helperText = msg
            else -> binding.passwordLayout.helperText = msg
        }
    }

    private fun closeKeyboardAndCursor() {
        binding.passwordET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val view: View? = this.currentFocus
                if (view != null) {
                    val inputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                }
                binding.passwordET.clearFocus()
            }
            true
        }
    }

    private fun clearHelperTexts() {
        binding.emailLayout.helperText = ""
        binding.passwordLayout.helperText = ""
    }

    override fun onSocialSignUpSuccess() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}