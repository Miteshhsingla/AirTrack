package com.example.test

import org.junit.Assert.*
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LoginUtilTest {

    @Test
    fun `empty email and password return false`() {
        val result = LoginUtil.validateLoginInput("", "")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty email and valid password return false`() {
        val result = LoginUtil.validateLoginInput("", "password123")
        assertThat(result).isFalse()
    }

    @Test
    fun `valid email and empty password return false`() {
        val result = LoginUtil.validateLoginInput("user@example.com", "")
        assertThat(result).isFalse()
    }

    @Test
    fun `valid email and valid password return true`() {
        val result = LoginUtil.validateLoginInput("user@example.com", "password123")
        assertThat(result).isTrue()
    }
}
