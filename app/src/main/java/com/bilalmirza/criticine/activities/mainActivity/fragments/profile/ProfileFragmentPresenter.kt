package com.bilalmirza.criticine.activities.mainActivity.fragments.profile

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.bilalmirza.criticine.model.user.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfileFragmentPresenter(private val view: ProfileFragmentView) {
    fun logOut() {
        Firebase.auth.signOut()
        view.onLogOutSuccess("Success")
    }

    fun getDetails() {
        Firebase.firestore.collection("users").document(Firebase.auth.currentUser?.uid!!)
            .addSnapshotListener { value, _ ->
                val userDetail = value?.data as HashMap<String, Any>
                val user = User(userDetail)
                view.onGetUserDetails(user)
            }
    }

    fun saveProfilePhoto(uri: Uri, context: Context) {
        view.onStartLoader()
        Firebase.storage.reference.child("profilePictures/${Firebase.auth.uid}").putFile(uri)
            .addOnSuccessListener {
                Firebase.storage.reference.child("profilePictures/${Firebase.auth.uid}").downloadUrl.addOnSuccessListener {
                    Firebase.firestore.collection("users")
                        .document(Firebase.auth.currentUser?.uid!!).update(
                            mapOf(
                                "profilePicture" to it.toString()
                            )
                        )
                    view.onStopLoader()
                    Toast.makeText(context, "Profile picture updated.", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(
                        context, "Error while showing the profile picture.", Toast.LENGTH_SHORT
                    ).show()
                }
            }.addOnFailureListener {
                Toast.makeText(
                    context, "Error while saving the profile picture.", Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun updateName(updatedName: String) {
        Firebase.firestore.collection("users").document(Firebase.auth.currentUser?.uid!!).update(
            mapOf(
                "name" to updatedName
            )
        ).addOnSuccessListener {
            view.onNameUpdateSuccess("Name updated successfully.")
        }.addOnFailureListener {
            view.onNameUpdateFail("Error while updating name.")
        }
    }
}