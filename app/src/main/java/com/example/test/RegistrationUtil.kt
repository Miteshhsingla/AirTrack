package com.example.test


object RegistrationUtil {

    fun validationRegistrationInput(
        username:String,
        password:String,
        confirmedPassword:String
    ):Boolean{
        if(username.isEmpty()||password.isEmpty())
            return false
        if(password!=confirmedPassword)
            return false
        if(password.count{it.isDigit()} < 2)
            return false

        return true
    }

}