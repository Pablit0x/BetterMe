package com.example.better_me.ui.composables.screens.select_avatar

import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * ViewModel class used to handle avatar changes and retrieve the current user avatar
 */
class AvatarViewModel : ViewModel() {
    /**
     * This function sets the avatar key value inside the online database to the particular value that represents the user avatar
     * @param userType User that used to determine where the user information is stored inside the online database
     * @param avatarID The ID of the selected avatar
     */

    fun setAvatar(userType: Int, avatarID: Int) {
        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().currentUser
        when (userType) {
            Constants.REGULAR_USER -> {
                db.collection("regularUsers").document(currentUser!!.uid)
                    .update("avatarID", avatarID)
            }
            Constants.SUPERVISOR_USER -> {
                db.collection("supervisorUsers").document(currentUser!!.uid)
                    .update("avatarID", avatarID)
            }
        }
    }

    /**
     * This function returns the avatarID from the online database
     * @param userID ID of the user for which we will get an avatar value
     * @param userType User that used to determine where the user information is stored inside the online database
     * @param myCallback return the avatarID as a callback
     */

    fun getAvatarID(userID: String, userType: Int, myCallback: (Int) -> Unit) {
        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().currentUser
        when (userType) {
            Constants.REGULAR_USER -> {
                val userRef = db.collection("regularUsers").document(userID)
                userRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val avatarID = task.result["avatarID"]
                        if (avatarID != null) {
                            myCallback(avatarID.toString().toInt())
                        } else {
                            myCallback(-1)
                        }
                    }
                }
            }
            Constants.SUPERVISOR_USER -> {
                val userRef = db.collection("supervisorUsers").document(currentUser!!.uid)
                userRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val avatarID = task.result["avatarID"]
                        if (avatarID != null) {
                            myCallback(avatarID.toString().toInt())
                        } else {
                            myCallback(-1)
                        }
                    }
                }
            }
        }
    }
}