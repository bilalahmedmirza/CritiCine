package com.bilalmirza.criticine.activities.signUp

interface SignUpView {
    fun onSignupSuccess(msg: String)
    fun onSignupFailure(msg: String)
    fun onShowError(msg: String, whichType: Int)
}