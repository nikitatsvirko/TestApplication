package com.application.nikita.testapplication.models

import com.activeandroid.query.Delete
import com.activeandroid.query.Select

class UserDao {

    fun createUser(): User {
        val user = User()
        user.save()
        return user
    }

    fun saveUser(user: User) = user.save()

    fun loadUser() = Select().from(User::class.java).execute<User>()

    fun deleteUser(user: User) {
       user.delete()
    }

    fun deleteAllUsers() {
        Delete().from(User::class.java).execute<User>()
    }
}