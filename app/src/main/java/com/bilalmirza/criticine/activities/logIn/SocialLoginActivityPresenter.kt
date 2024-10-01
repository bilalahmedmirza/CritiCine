package com.bilalmirza.criticine.activities.logIn

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.bilalmirza.criticine.model.user.User
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SocialLoginActivityPresenter(private val socialView: SocialLoginActivityView) {
    private val googleIdOption: GetGoogleIdOption =
        GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
            .setServerClientId("399787163182-s4iag12kd59t5mj0g1kh8n0etclko32r.apps.googleusercontent.com")
            .setAutoSelectEnabled(false).build()

    fun googleLogIn(
        activity: Activity
    ) {
        val request: GetCredentialRequest =
            GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
        val credentialManager = CredentialManager.create(activity)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = credentialManager.getCredential(
                    context = activity, request = request
                )
                handleSignIn(result, activity)
            } catch (e: GetCredentialException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse, activity: Activity) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val firebaseCredential = GoogleAuthProvider.getCredential(
                            googleIdTokenCredential.idToken, null
                        )
//                        checkIfMailExists(googleIdTokenCredential.id, activity)
                        Firebase.auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful) {
                                    Log.d(
                                        "googleLogInLogSuccess",
                                        "signInWithCredential: Great Success!"
                                    )
                                    Firebase.firestore.collection("users")
                                        .document(Firebase.auth.currentUser?.uid!!).get()
                                        .addOnSuccessListener {
                                            if (!it.exists()) {
                                                val user = User(
                                                    name = Firebase.auth.currentUser?.displayName.toString(),
                                                    email = Firebase.auth.currentUser?.email.toString(),
                                                    profilePicture = Firebase.auth.currentUser?.photoUrl.toString(),
                                                    loginType = "google"
                                                )
                                                Firebase.firestore.collection("users")
                                                    .document(Firebase.auth.currentUser?.uid!!)
                                                    .set(user.createMap()).addOnSuccessListener {
                                                        socialView.onSocialSignUpSuccess()
                                                    }.addOnFailureListener {
                                                        Toast.makeText(
                                                            activity,
                                                            "Could not log in using Google.",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                            } else {
                                                socialView.onSocialSignUpSuccess()
                                            }
                                        }.addOnFailureListener {
                                            Toast.makeText(
                                                activity,
                                                "Could not log in using Google.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                } else {
                                    Log.w(
                                        "googleLogInLogSuccess",
                                        "signInWithCredential: Failure",
                                        task.exception
                                    )
                                    Toast.makeText(
                                        activity, "Sign In with Google Failed.", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("googleLogInLog", "Received an invalid google id token response", e)
                    }
                } else {
                    Log.e("googleLogInLog", "Unexpected type of credential")
                }
            }

            else -> {
                Toast.makeText(
                    activity, "Sign In with Google Failed.", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

//    private fun checkIfMailExists(email: String, activity: Activity) {
//        Firebase.firestore.collection("users").whereEqualTo("email", email).get()
//            .addOnSuccessListener {
//                if (it.isEmpty.not()) {
//                    Toast.makeText(activity, "exists", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(activity, "not exists", Toast.LENGTH_SHORT).show()
//                }
//            }.addOnFailureListener {
//                Log.d("checkIfMailExists", "checkIfMailExists: Error searching for mail.")
//            }
//    }
}