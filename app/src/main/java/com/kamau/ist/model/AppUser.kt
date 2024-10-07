package com.kamau.ist.model

data class AppUser(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val profilePictureUrl: String = "",
// Possible values: "Admin" or "User"
    val role: String = ""
)
