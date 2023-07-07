package com.example.better_me.ui.composables.screens.regular.add_activity

import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.Activity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * AddActivityAlertDialog ViewModel class used to validate user inputs and add activity to the online database
 */
class AddActivityViewModel : ViewModel() {
    /**
     * This function validates the activity name input
     * @param activityName String that represents the activity name
     */
    fun validateActivityName(activityName: String): Boolean {
        return activityName.isNotEmpty()
    }

    /**
     * This function represents the activity type input
     * @param activityType String that represents the activity type
     */
    fun validateActivityType(activityType: String): Boolean {
        return activityType.isNotEmpty()
    }

    /**
     * This function adds the activity object to the online database
     * @param activity Activity object that will be added
     * @param uid ID of the user to with the added activity will be attached
     * @param date The date which represents for which day the activity was added
     */

    fun addActivity(activity: Activity, uid: String, date: String) {
        val db = Firebase.firestore
        val activityID = activity.id as String
        db.collection("records").document(uid).collection(date).document(activityID).set(activity)
    }
}