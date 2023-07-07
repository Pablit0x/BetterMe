package com.example.better_me.ui.composables.screens.supervisor.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.RegularUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

/**
 * SupervisorMainScreen ViewModel class used to acquire the supervised users from the database
 */
class SupervisorMainViewModel : ViewModel() {
    val firebaseAuth = FirebaseAuth.getInstance()
    private val currentSupervisor = firebaseAuth.currentUser
    private val db = Firebase.firestore
    private val supervisedUsers = db.collection("supervisorUsers").document(currentSupervisor!!.uid)

    /**
     * This function returns users supervised by the currently logged in supervisor
     */
    fun getSupervisedUsers(myCallback: (SnapshotStateList<RegularUser?>) -> Unit) {
        val users = mutableStateListOf<RegularUser?>()
        supervisedUsers.get().addOnSuccessListener { documents ->
            val userUIDs = (documents.data?.get("supervised")) as? MutableList<*>
            if (userUIDs != null) {
                for (uid in userUIDs) {
                    db.collection("regularUsers").whereEqualTo("uid", uid).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (document in task.result) {
                                    users.add(document.toObject())
                                    users.sortBy { it!!.username }
                                }
                                myCallback(users)
                            }
                        }
                }
            }
        }
    }


    /**
     * This function deletes the relation between a specified user from the list of supervised users
     * @param userID The ID of the user which will no longer be supervised by the currently logged in supervisor
     */
    fun deleteSupervisedUser(userID: String) {
        supervisedUsers.update("supervised", FieldValue.arrayRemove(userID))
    }
}