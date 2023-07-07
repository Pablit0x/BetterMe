package com.example.better_me.ui.composables.screens.regular.add_supervisor

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.SupervisorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * AddSupervisorAlertDialog ViewModel class used to validate user input and add supervisor requests to the online database
 */

class AddSupervisorViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    /**
     * This function the adds the currently logged in user identifier to the requests field of the supervisor in the online database
     * @param email String variable that represents the supervisor email
     */

    fun addSupervisor(email: String) {
        db.collection("supervisorUsers").whereEqualTo("email", email).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val updatedRequests = mutableListOf<String>()
                    (document["requests"] as MutableList<String>?)?.let { updatedRequests.addAll(it) }
                    updatedRequests.add(firebaseAuth.currentUser?.uid.toString())
                    val updatedDoc = SupervisorUser(
                        username = document["username"].toString(),
                        email = document["email"].toString(),
                        userType = document["userType"].toString(),
                        uid = document["uid"].toString(),
                        requests = updatedRequests,
                        supervised = document["supervised"] as MutableList<String>?
                    )
                    db.collection("supervisorUsers").document(document["uid"].toString())
                        .set(updatedDoc)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("fail", exception.toString())
            }
    }

    /**
     * This function validates if the given input is a valid email
     * @param email String variable that represents email
     * @return Boolean variable that represents if the given email is valid or not
     */

    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }
}