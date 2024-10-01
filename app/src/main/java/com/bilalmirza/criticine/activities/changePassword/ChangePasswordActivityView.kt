package com.bilalmirza.criticine.activities.changePassword

interface ChangePasswordActivityView {
    fun onPassChangeSuccess(msg: String)
    fun onPassChangeFail(msg: String)
    fun onShowError(msg: String, type: Int)
}