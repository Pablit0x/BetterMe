package com.example.better_me.ui.composables.screens.supervisor.user_activities

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.Activity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.text.DateFormatSymbols

/**
 * SupervisorViewScreen ViewModel class used to get activities from the online database
 */
class SupervisorViewViewModel : ViewModel() {

    /**
     * This function returns only public activities for a given user in a given day
     * @param date The date for which the activities should be returned
     * @param uid The ID of the user who owns these activities
     * @param myCallback Returns the activity list as a callback
     */

    fun getPublicActivities(
        date: String,
        uid: String,
        myCallback: (SnapshotStateList<Activity>) -> Unit
    ) {
        val db = Firebase.firestore
        val activities = db.collection("records").document(uid).collection(date)
        activities.orderBy("dateCreated").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list = mutableStateListOf<Activity>()
                for (document in task.result) {
                    if (document["privacyType"] == "Public")
                        list.add(document.toObject())
                }
                myCallback(list)
            }
        }
    }

    /**
     * This function returns the feedback comment from the online database
     * @param date The date for which the feedback comment should be returned
     * @param uid The ID of the user to which the comment was assigned
     * @param myCallback Returns the feedback comment as a callback
     */
    fun getFeedback(date: String, uid: String, myCallback: (String) -> Unit) {
        val db = Firebase.firestore
        val activities =
            db.collection("records").document(uid).collection("feedbacks").document(date)
        activities.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val feedback = task.result["text"]
                if (feedback != null) {
                    myCallback(feedback.toString())
                } else {
                    myCallback("")
                }
            }
        }
    }

    /**
     * This function returns the rating value from the online database
     * @param date The date for which the rating value should be returned
     * @param uid The ID of the user to which the rating was assigned
     * @param myCallback returns the rating as a callback
     */
    fun getRating(date: String, uid: String, myCallback: (Int) -> Unit) {
        val db = Firebase.firestore
        val activities = db.collection("records").document(uid).collection("ratings").document(date)
        activities.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val rating = task.result["rating"]
                if (rating != null) {
                    myCallback(rating.toString().toInt())
                } else {
                    myCallback(0)
                }
            }
        }
    }

    fun getMonth(month: Int): String? {
        return DateFormatSymbols().months[month - 1]
    }
}