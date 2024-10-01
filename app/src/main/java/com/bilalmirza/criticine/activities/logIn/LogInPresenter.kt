package com.bilalmirza.criticine.activities.logIn

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInPresenter(private val view: LogInView) {
    fun logIn(email: String, password: String) {
        if (validateCredentials(email, password)) {
            Firebase.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                view.onLogInSuccess("Success")
            }.addOnFailureListener {
                view.onLogInFailure("Failure")
            }
        }
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        if (email.isBlank()) {
            view.onShowError("Please enter an email.", 0)
            return false
        } else if (password.isBlank()) {
            view.onShowError("Please enter the password.", 1)
            return false
        }
        return true
    }
}