package com.bilalmirza.criticine.activities.forgotPassword

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.bilalmirza.criticine.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordActivityView {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var myPresenter: ForgotPasswordActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  changing color of status bar text programmatically
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        //  click listener
        clickListener()
        //  initialising the presenter
        myPresenter = ForgotPasswordActivityPresenter(this)
    }

    private fun clickListener() {
        binding.forgotEmailET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val view: View? = this.currentFocus
                if (view != null) {
                    val inputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                }
                binding.forgotEmailET.clearFocus()
            }
            true
        }
        binding.forgotPasswordBtn.setOnClickListener {
            val email = binding.forgotEmailET.text.toString()
            myPresenter.resetPassword(email)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        binding.forgotEmailET.setText("")
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}