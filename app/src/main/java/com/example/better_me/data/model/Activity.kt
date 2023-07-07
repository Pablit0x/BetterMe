package com.example.better_me.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Activity(
    val name: String? = null,
    val type: String? = null,
    val done: Boolean? = null,
    val id: String? = null,
    val createdBy: Int? = null,
    val privacyType: String? = null,
    @ServerTimestamp
    val dateCreated: Date? = null
)

