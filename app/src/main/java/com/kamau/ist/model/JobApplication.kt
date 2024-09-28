package com.kamau.ist.model

data class JobApplication(
    val userId: String = "",
    val jobId: String = "",
    val coverLetter: String = "",
    val appliedAt: Long = System.currentTimeMillis()
)
