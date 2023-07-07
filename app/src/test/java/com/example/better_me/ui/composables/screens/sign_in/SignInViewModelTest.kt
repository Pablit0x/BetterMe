package com.example.better_me.ui.composables.screens.sign_in

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class SignInViewModelTest {
    lateinit var signInViewModel : SignInViewModel

    @Before
    fun setUp() {
        signInViewModel = SignInViewModel()
    }

    @Test
    fun testWithBlankPassword() {
        val result = signInViewModel.validatePassword("")
        assertEquals(result, false)
    }

    @Test
    fun testTooShortPassword(){
        val result = signInViewModel.validatePassword(password = "12345")
        assertEquals(result, false)
    }

    @Test
    fun testWithValidPassword(){
        val result = signInViewModel.validatePassword(password = "123456")
        assertEquals(result, true)
    }
}