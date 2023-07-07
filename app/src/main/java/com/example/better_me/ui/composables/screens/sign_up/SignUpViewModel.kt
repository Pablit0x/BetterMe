package com.example.better_me.ui.composables.screens.sign_up

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.better_me.data.model.RegularUser
import com.example.better_me.data.model.SupervisorUser
import com.example.better_me.data.screens.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * SignUpScreen ViewModel class used to validate email and password and handle the authentication process
 */
class SignUpViewModel : ViewModel() {

    /**
     * This function validates if the given input is a valid email
     * @param email String variable that represents email
     * @return Boolean variable that represents if the given email is valid or not
     */

    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }

    /**
     * This function validates if the given input is a valid password
     * @param password String variable that represents a password
     * @return Boolean variable that represents if the given password is valid or not
     */

    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length > 5
    }

    /**
     * This function validates if the given input is not empty
     * @param username String variable that represents the username
     * @return Boolean variable that represents if the given username is valid or not
     */

    fun validateUsername(username: String): Boolean {
        return username.isNotEmpty()
    }

    /**
     * This function validates if the given input is not empty
     * @param userType String variable that represents user type
     * @return Boolean variable that represents if the given user type is valid or not
     */

    fun validateUserType(userType: String): Boolean {
        return userType.isNotEmpty()
    }

    /**
     * This function uses the firebase authentication service to sign up the new users
     * @param username String variable that represents username
     * @param email String variable that represents email
     * @param userType String variable that represents user type
     * @param password String variable that represents a password
     * @param navController Navigation controller to navigate between screens
     */

    fun firebaseSignUp(
        firebaseAuth: FirebaseAuth,
        username: String,
        email: String,
        userType: String,
        password: String,
        navController: NavController,
        context: Context
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val firebaseUser = firebaseAuth.currentUser!!
                        navController.navigate(Screen.LoginScreen.route)
                        val db = Firebase.firestore
                        when (userType) {
                            "Regular" -> {
                                val user = RegularUser(
                                    username = username,
                                    email = email,
                                    userType = userType,
                                    uid = firebaseUser.uid
                                )
                                db.collection("regularUsers").document(firebaseUser.uid).set(user)
                            }
                            "Supervisor" -> {
                                val user = SupervisorUser(
                                    username = username,
                                    email = email,
                                    userType = userType,
                                    uid = firebaseUser.uid
                                )
                                db.collection("supervisorUsers").document(firebaseUser.uid)
                                    .set(user)
                            }
                        }
                    }
            }
            .addOnFailureListener {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}