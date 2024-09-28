package com.kamau.ist.feature.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kamau.ist.model.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//class AdminViewModel {
//}
@HiltViewModel
class AdminViewModel @Inject constructor(
    private val firestore: FirebaseFirestore, // For interacting with Firestore
    private val firebaseAuth: FirebaseAuth // For authentication-related actions
) : ViewModel() {

    // MutableState to track the loading state when posting a job
    var isLoading by mutableStateOf(false)
        private set

    // MutableState to track any error messages
    var errorMessage by mutableStateOf("")
        private set

    // Function to handle job posting logic
    fun postNewJob(job: Job, onJobPosted: () -> Unit) {
        isLoading = true
        val jobId = firestore.collection("jobs").document().id // Generate a unique job ID
        firestore.collection("jobs").document(jobId)
            .set(job)
            .addOnSuccessListener {
                isLoading = false
                onJobPosted()
            }
            .addOnFailureListener { exception ->
                isLoading = false
                errorMessage = "Error posting job: ${exception.message}"
            }
    }

    // Function to logout the admin
    fun logoutAdmin(onLogoutSuccess: () -> Unit) {
        firebaseAuth.signOut()
        onLogoutSuccess()
    }
}
