package com.example.better_me.ui.composables.screens.sign_in

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.better_me.data.screens.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * SignInScreen ViewModel class used to validate email and password and handle the authentication process
 */
class SignInViewModel : ViewModel() {

    /**
     * This function validates if the given input is a valid email
     * @param email String variable that represents email
     * @return Boolean variable that represents if the given email is valid or not
     */

    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }

    /**
     * This function uses the firebase authentication service to log in the existing user
     * @param email String variable that represents email
     * @param password String variable that represents a password
     * @param navController Navigation controller to navigate between screens
     */

    fun firebaseLogin(
        firebaseAuth: FirebaseAuth,
        email: String,
        password: String,
        navController: NavController,
        context : Context
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val db = Firebase.firestore
                val doc = db.collection("regularUsers")
                    .whereEqualTo("email", firebaseAuth.currentUser!!.email)
                doc.get().addOnSuccessListener { documents ->
                    if (documents.size() == 1) {
                        navController.navigate(Screen.RegularMainScreen.route)
                    } else {
                        navController.navigate(Screen.SupervisorMainScreen.route)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    /**
     * This function validates if the given input is a valid password
     * @param password String variable that represents a password
     * @return Boolean variable that represents if the given password is valid or not
     */

    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length > 5
    }
}