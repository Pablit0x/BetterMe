package com.example.better_me.ui.composables.screens.supervisor.add_feedback

import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.Feedback
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * AddFeedbackDialog ViewModel class used to add feedback comments to the online database
 */
class AddFeedbackViewModel : ViewModel() {
    /**
     * This function adds the activity object to the online database
     * @param comment String variable that represents the feedback comment
     * @param uid ID of the user to with the feedback comment will be attached
     * @param date The date which represents for which day the feedback was added
     */

    fun addFeedback(uid: String, comment: String, date: String) {
        val db = Firebase.firestore
        val feedback = Feedback(text = comment)
        db.collection("records").document(uid).collection("feedbacks").document(date).set(feedback)
    }
}