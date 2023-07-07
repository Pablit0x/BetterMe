package com.example.better_me.ui.composables.screens.regular.user_supervisors

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.SupervisorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

/**
 * RegularSupervisorScreen ViewModel class used to retrieve user supervisors from the database
 */
class RegularSupervisorsViewModel : ViewModel() {
    private val db = Firebase.firestore

    /**
     * This function returns the supervisors assigned to the currently logged in user
     * @param myCallback returns the supervisors assigned to the currently logged in user as the callback
     */

    fun getUserSupervisors(myCallback: (SnapshotStateList<SupervisorUser>) -> Unit) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val userSupervisors = db.collection("supervisorUsers")
            .whereArrayContains("supervised", firebaseAuth.currentUser!!.uid)
        userSupervisors.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val users = mutableStateListOf<SupervisorUser>()
                for (document in task.result) {
                    users.add(document.toObject())
                }
                myCallback(users)
            }
        }
    }

    /**
     * This function deletes the relation between users and their supervisors
     * @param supervisorID The ID of the supervisor which will no longer be a currently logged in user supervisor
     */
    fun deleteSupervisor(supervisorID: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val supervisorDoc = db.collection("supervisorUsers").document(supervisorID)
        supervisorDoc.update("supervised", FieldValue.arrayRemove(firebaseAuth.currentUser!!.uid))
    }
}