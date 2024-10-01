package com.bilalmirza.criticine.activities.signUp

import android.util.Patterns
import com.bilalmirza.criticine.model.user.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpPresenter(private val view: SignUpView) {
    private val specialCharPattern = Regex("[^A-Za-z0-9]")

    private fun validateCredentials(
        username: String, email: String, password: String, confirmPassword: String
    ): Boolean {
        return if (username.isBlank()) {
            view.onShowError("Username cannot be empty.", 0)
            false
        } else if (!isTextOnly(username)) {
            view.onShowError("Username cannot have numbers.", 4)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.onShowError("Invalid email.", 1)
            return false
        } else if (!specialCharPattern.containsMatchIn(password)) {
            view.onShowError("Password must contain at least one special character", 5)
            false
        } else if (password.length < 8) {
            view.onShowError("Password must be of 8 characters.", 2)
            false
        } else if (password != confirmPassword) {
            view.onShowError("Passwords do not match.", 3)
            return false
        } else {
            true
        }
    }

    fun signUp(name: String, email: String, password: String, confirmPassword: String) {
        if (validateCredentials(name, email, password, confirmPassword)) {
            Firebase.auth.createUserWithEmailAndPassword(
                email, password
            ).addOnSuccessListener {
                val user = User(name, email, "", "manual")
                Firebase.firestore.collection("users").document(Firebase.auth.currentUser?.uid!!)
                    .set(user.createMap()).addOnSuccessListener {
                        view.onSignupSuccess("Successful")
                    }.addOnFailureListener {
                        view.onSignupFailure(it.message ?: "Failure")
                    }
            }.addOnFailureListener {
                view.onSignupFailure(it.message ?: "Failure")
            }
        }
    }

    private fun isTextOnly(input: String): Boolean {
        val regex = Regex("^[a-zA-Z\\s]+$")

        return regex.matches(input)
    }
}