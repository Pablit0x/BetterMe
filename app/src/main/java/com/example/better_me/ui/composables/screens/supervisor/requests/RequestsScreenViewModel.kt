package com.example.better_me.ui.composables.screens.supervisor.requests

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
 * RequestScreen ViewModel class used to handle the requests
 */
class RequestsScreenViewModel : ViewModel() {
    val firebaseAuth = FirebaseAuth.getInstance()
    private val currentSupervisor = firebaseAuth.currentUser
    private val db = Firebase.firestore
    private val requests = db.collection("supervisorUsers").document(currentSupervisor!!.uid)

    /**
     * This function returns all requests for the currently logged in supervisor
     * @param myCallback Returns the list of regular objects which are representing the users that send a request to that specific supervisor
     */
    fun getAllRequests(myCallback: (SnapshotStateList<RegularUser>) -> Unit) {
        val users = mutableStateListOf<RegularUser>()
        requests.get().addOnSuccessListener { documents ->
            val userUIDs = (documents.data?.get("requests")) as? MutableList<*>
            if (userUIDs != null) {
                for (uid in userUIDs) {
                    db.collection("regularUsers").whereEqualTo("uid", uid).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (document in task.result) {
                                    users.add(document.toObject())
                                }
                                myCallback(users)
                            }
                        }
                }
            }
        }
    }

    /**
     * This function transfers the regular user ID from the requests field to the supervised field in the online database
     * @param uid ID of the user which uid will be transferred to the supervised field
     */

    fun acceptRequest(uid: String) {
        requests.update("requests", FieldValue.arrayRemove(uid))
        requests.update("supervised", FieldValue.arrayUnion(uid))
    }

    /**
     * This function deletes the user id from the requests field
     * @param uid User ID, which will be deleted from the requests field
     */

    fun declineRequest(uid: String) {
        requests.update("requests", FieldValue.arrayRemove(uid))
    }
}