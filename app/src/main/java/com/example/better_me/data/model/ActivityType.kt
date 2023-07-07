package com.example.better_me.data.model

sealed class ActivityType(
    val name: String,
) {

    object Sleep : ActivityType(name = "Sleep")

    object Work : ActivityType(
        name = "Work"
    )

    object Exercise : ActivityType(
        name = "Exercise"
    )

    object Study : ActivityType(
        name = "Study"
    )

    object Entertainment : ActivityType(
        name = "Entertainment"
    )

    object Other : ActivityType(
        name = "Other Activity"
    )
}