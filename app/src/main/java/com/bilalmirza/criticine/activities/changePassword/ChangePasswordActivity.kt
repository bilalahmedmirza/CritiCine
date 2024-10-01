package com.bilalmirza.criticine.activities.changePassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bilalmirza.criticine.activities.mainActivity.MainActivity
import com.bilalmirza.criticine.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity(), ChangePasswordActivityView {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var myPresenter: ChangePasswordActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  initialising the presenter
        myPresenter = ChangePasswordActivityPresenter(this)
        //  click listeners
        clickListeners()
    }

    private fun clickListeners() {
        binding.confirmNewPasswordET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val view: View? = this.currentFocus
                if (view != null) {
                    val inputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                }
                binding.confirmNewPasswordET.clearFocus()
            }
            true
        }
        binding.changePasswordBtn.setOnClickListener {
            val currentPassword = binding.currentPasswordET.text.toString()
            val newPassword = binding.passwordChangeET.text.toString()
            val confirmPassword = binding.confirmNewPasswordET.text.toString()
            clearHelperTexts()

            myPresenter.changePassword(currentPassword, newPassword, confirmPassword)
        }
    }

    private fun clearHelperTexts() {
        binding.passwordChangeLayout.helperText = ""
        binding.confirmNewPasswordLayout.helperText = ""
    }

    override fun onPassChangeSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun onPassChangeFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onShowError(msg: String, type: Int) {
        when (type) {
            0 -> {
                binding.passwordChangeLayout.helperText = msg
            }

            1 -> {
                binding.passwordChangeLayout.helperText = msg
            }

            2 -> {
                binding.confirmNewPasswordLayout.helperText = msg
            }

            3 -> {
                binding.currentPasswordLayout.helperText = msg
            }

            4 -> {
                binding.currentPasswordLayout.helperText = msg
            }
        }
    }
}