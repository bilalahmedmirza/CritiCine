package com.bilalmirza.criticine.activities.forgotPassword

import android.util.Patterns
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivityPresenter(private val view: ForgotPasswordActivityView) {
    fun resetPassword(email: String) {
        if (validateCredentials(email)) {
            Firebase.auth.sendPasswordResetEmail(email).addOnSuccessListener {
                view.onSuccess("Link to reset password sent at $email")
            }.addOnFailureListener { view.onFail("Error sending link.") }
        }
    }

    private fun validateCredentials(
        email: String
    ): Boolean {
        return if (email.isBlank()) {
            view.onError("Please enter an email.", 2)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.onError("Invalid email.", 1)
            return false
        } else {
            true
        }
    }
}