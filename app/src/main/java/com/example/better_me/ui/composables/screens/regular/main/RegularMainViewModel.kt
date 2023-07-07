package com.example.better_me.ui.composables.screens.regular.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.text.DateFormatSymbols

/**
 * RegularMainScreen ViewModelClass used to get and alter activities from the online database
 */
class RegularMainViewModel : ViewModel() {
    /**
     * This function deletes the activity record from the database
     * @param activity The activity object that will be deleted
     * @param uid The ID of the user whose activity will be deleted
     * @param date The date of that activity
     */

    fun deleteActivity(activity: Activity, uid: String, date: String) {
        val db = Firebase.firestore
        val activityID = activity.id as String
        db.collection("records").document(uid).collection(date).document(activityID).delete()
            .addOnSuccessListener { Log.e("del", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.e("del", "Error deleting document", e) }
    }

    /**
     * This function updates the activity status inside the database whenever the user changes its value through UI
     * @param done The new boolean status
     * @param activity The activity object with the old boolean status
     * @param date The date of the activity
     */
    fun setActivityStatus(done: Boolean, activity: Activity, date: String) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val activityID = activity.id as String
        val db = Firebase.firestore
        val documentRef =
            db.collection("records").document(uid).collection(date).document(activityID)
        documentRef.update("done", done)
    }


    /**
     * This function returns all activities for a given user in a given day
     * @param date The date for which the activities should be returned
     * @param uid The user ID of the user which ones this activities
     * @param myCallback Returns the activity list as a callback
     */
    fun getAllActivities(
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