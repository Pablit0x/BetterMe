package com.example.better_me.ui.composables.screens.sign_up

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import kotlin.math.sign

class SignUpViewModelTest {
    lateinit var signUpViewModel: SignUpViewModel
    @Before
    fun setUp() {
        signUpViewModel = SignUpViewModel()
    }

    @Test
    fun testWithBlankPassword() {
        val result = signUpViewModel.validatePassword("")
        assertEquals(result, false)
    }

    @Test
    fun testTooShortPassword(){
        val result = signUpViewModel.validatePassword(password = "12345")
        assertEquals(result, false)
    }

    @Test
    fun testWithValidPassword(){
        val result = signUpViewModel.validatePassword(password = "123456")
        assertEquals(result, true)
    }

    @Test
    fun testWithBlankUsername() {
        val result = signUpViewModel.validateUsername("")
        assertEquals(result, false)
    }

    @Test
    fun testWithValidUsername(){
        val result = signUpViewModel.validateUsername("TestUser")
        assertEquals(result, true)

    }

    @Test
    fun testWithBlankUserType() {
        val result = signUpViewModel.validateUserType("")
        assertEquals(result, false)
    }

    @Test
    fun testWithRegularUserType(){
        val result = signUpViewModel.validateUsername("Regular")
        assertEquals(result, true)
    }

    @Test
    fun testWithSupervisorUserType(){
        val result = signUpViewModel.validateUsername("Supervisor")
        assertEquals(result, true)
    }
}