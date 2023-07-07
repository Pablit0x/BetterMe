package com.example.better_me.data.model

data class SupervisorUser(
    val username: String? = null,
    val email: String? = null,
    val userType: String? = null,
    val requests: MutableList<String>? = null,
    val supervised: MutableList<String>? = null,
    val uid: String? = null,
    val avatarID: Int? = null
)