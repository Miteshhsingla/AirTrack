package com.example.test


object LoginUtil {
    fun validateLoginInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty())
            return false


        return true
    }
}
