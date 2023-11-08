package com.example.test

import org.junit.Assert.*
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test



class SignUpActivityTest{
    @Test
    fun `empty username return false`(){
        val result = RegistrationUtil.validationRegistrationInput(
            "",
            "123",
            "123"
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `incorrect confirm password return false`(){
        val result = RegistrationUtil.validationRegistrationInput(
            "Mitesh",
            "1232455",
            "123455"
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `empty password return false`(){
        val result = RegistrationUtil.validationRegistrationInput(
            "samarth",
            "",
            ""
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `less than 2 digit password return false`(){
        val result = RegistrationUtil.validationRegistrationInput(
            "Mitesh",
            "12asd",
            "12asd"
        )
        Truth.assertThat(result).isTrue()
    }

}