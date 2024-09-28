package com.kamau.ist.model

data class AppUser(
    val uid: String = "",
    val email: String = "",
// Possible values: "Admin" or "User"
    val role: String = ""
)
