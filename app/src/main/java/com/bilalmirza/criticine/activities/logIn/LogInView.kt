package com.bilalmirza.criticine.activities.logIn

interface LogInView {
    fun onLogInSuccess(msg: String)
    fun onLogInFailure(msg: String)
    fun onShowError(msg: String, whichType: Int)
}