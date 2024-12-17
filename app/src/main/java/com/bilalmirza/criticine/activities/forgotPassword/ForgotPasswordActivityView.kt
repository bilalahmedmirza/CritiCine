package com.bilalmirza.criticine.activities.forgotPassword

interface ForgotPasswordActivityView {
    fun onSuccess(msg: String)
    fun onFail(msg: String)
    fun onError(msg: String, type: Int)
}