package com.bilalmirza.criticine.activities.mainActivity.fragments.profile

import com.bilalmirza.criticine.model.user.User

interface ProfileFragmentView {
    fun onLogOutSuccess(msg: String)
    fun onGetUserDetails(user: User)
    fun onStartLoader()
    fun onStopLoader()
    fun onNameUpdateSuccess(msg: String)
    fun onNameUpdateFail(msg: String)
}