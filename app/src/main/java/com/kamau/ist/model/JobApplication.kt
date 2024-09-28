package com.kamau.ist.model

import com.google.firebase.Timestamp

data class JobApplication(
    val userId: String = "",
    val jobId: String = "",
    val coverLetter: String = "",
    val resumeUrl: String? = null, // Optional URL for resume
    val appliedOn: Timestamp = Timestamp.now() // Automatically set the application time
//    val appliedAt: Long = System.currentTimeMillis()
)
