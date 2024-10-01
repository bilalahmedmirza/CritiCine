package com.bilalmirza.criticine.activities.changePassword

import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangePasswordActivityPresenter(private val view: ChangePasswordActivityView) {
    fun changePassword(currentPassword: String, newPassword: String, confirmPassword: String) {
        if (validatePassword(currentPassword, newPassword, confirmPassword)) {
            val credentials =
                EmailAuthProvider.getCredential(Firebase.auth.currentUser?.email!!, currentPassword)
            Firebase.auth.currentUser!!.reauthenticate(credentials).addOnSuccessListener {
                Firebase.auth.currentUser!!.updatePassword(confirmPassword).addOnSuccessListener {
                    view.onPassChangeSuccess("Password changed successfully.")
                    Log.d("changePassword", "changePassword: Great Success!")
                }.addOnFailureListener {
                    view.onPassChangeFail("Error while changing password.")
                    Log.d("changePassword", "changePassword: ${it.localizedMessage}")
                }
            }.addOnFailureListener {
                view.onPassChangeFail("Error while changing password.")
                Log.d("changePassword", "changePassword: ${it.localizedMessage}")
            }
        }
    }

    private fun validatePassword(
        currentPassword: String, newPassword: String, confirmPassword: String
    ): Boolean {
        return if (newPassword.length < 8) {
            view.onShowError("Password must be of 8 characters.", 0)
            false
        } else if (newPassword.isBlank()) {
            view.onShowError("Enter a new password.", 1)
            false
        } else if (newPassword != confirmPassword) {
            view.onShowError("Passwords do not match.", 2)
            false
        } else if (currentPassword.length < 8) {
            view.onShowError("Password must be of 8 characters.", 3)
            false
        } else if (currentPassword.isBlank()) {
            view.onShowError("Enter the current password.", 4)
            false
        } else {
            true
        }
    }
}