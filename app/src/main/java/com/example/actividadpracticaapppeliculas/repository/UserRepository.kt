package com.example.actividadpracticaapppeliculas.repository

import com.example.actividadpracticaapppeliculas.dao.UserDao
import com.example.actividadpracticaapppeliculas.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }
}