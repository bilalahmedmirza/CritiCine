package com.bilalmirza.criticine.activities.forgotPassword

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivityPresenter(private val view: ForgotPasswordActivityView) {
    fun resetPassword(email: String) {
        Firebase.auth.sendPasswordResetEmail(email).addOnSuccessListener {
            view.onSuccess("Link to reset password sent at $email")
        }.addOnFailureListener { view.onFail("Error sending link.") }
    }
}