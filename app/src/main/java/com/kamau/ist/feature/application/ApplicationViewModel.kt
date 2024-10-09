package com.kamau.ist.feature.application

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kamau.ist.model.JobApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//class ApplicationViewModel {
//}
@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    fun applyForJob(userId: String, jobId: String, coverLetter: String, skills: String) {
        val application = JobApplication(
            userId = userId,
            jobId = jobId,
            coverLetter = coverLetter,
            skills = skills
        )

        firestore.collection("applications").add(application)
            .addOnSuccessListener {
                // Application submitted successfully
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}
