package com.application.nikita.testapplication.models

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table



@Table(name = "User", id = "_id")
class User: Model {

    @Column(name = "login")
    var login: String? = null
    @Column(name = "token")
    var token: String? = null
    @Column(name = "userId")
    var userId: Int = 0

    constructor(login: String, token: String, userId: Int) {
        this.login = login
        this.token = token
        this.userId = userId
    }

    constructor()

    fun getInfo(): String = "Login: $login\n" +
            "Token: $token\n" +
            "UserID: $userId\n"
}
