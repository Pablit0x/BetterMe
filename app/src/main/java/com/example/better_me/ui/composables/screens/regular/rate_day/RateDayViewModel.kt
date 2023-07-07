package com.example.better_me.ui.composables.screens.regular.rate_day

import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.Rating
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * RateDayAlertDialog ViewModel class used to save the rating inside the online database
 */
class RateDayViewModel : ViewModel() {
    /**
     * This function saves/updates the rating inside the online database
     * @param uid ID of the user to with the rating will be attached
     * @param rating The Int variable represents the user rating
     * @param date The date which represents for which day the activity was added
     */

    fun saveRating(uid: String, rating: Int, date: String) {
        val db = Firebase.firestore
        val userRating = Rating(rating = rating)
        db.collection("records").document(uid).collection("ratings").document(date).set(userRating)
    }
}