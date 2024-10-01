package com.bilalmirza.criticine.model.user

class User(
    var name: String = "",
    var email: String = "",
    var profilePicture: String = "",
    var loginType: String = ""
) {
    fun createMap(): HashMap<String, Any> {
        return HashMap<String, Any>().apply {
            this["name"] = name
            this["email"] = email
            this["profilePicture"] = profilePicture
            this["loginType"] = loginType
        }
    }

    constructor(map: HashMap<String, Any>) : this() {
        this.name = map["name"] as String
        this.email = map["email"] as String
        this.profilePicture = (map["profilePicture"] as? String) ?: ""
        this.loginType = map["loginType"] as String
    }
}