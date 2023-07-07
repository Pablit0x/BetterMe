package com.example.better_me.ui.composables.screens.forgot_password

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.better_me.R
import com.example.better_me.data.screens.Screen
import com.google.firebase.auth.FirebaseAuth

/**
 * ForgotPasswordScreen ViewModel class used to validate user input
 */
class ForgotPasswordViewModel : ViewModel() {
    /**
     * This function validates if the given input is a valid email
     * @param email String variable that represents email
     * @return Boolean variable that represents if the given email is valid or not
     */
    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }

    fun sendPasswordResetEmail(
        email : String,
        context : Context,
        navController: NavController,
        firebaseAuth: FirebaseAuth

    ){
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.recovery_email_was_sent),
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(Screen.LoginScreen.route)
                } else {
                    Toast.makeText(
                        context,
                        task.exception!!.message.toString(), Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}