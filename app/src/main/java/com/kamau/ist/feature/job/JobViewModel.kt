package com.kamau.ist.feature.job

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kamau.ist.model.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    var jobList by mutableStateOf(listOf<Job>())
        private set

    init {
        loadJobs()
    }

    private fun loadJobs() {
        firestore.collection("jobs").get()
            .addOnSuccessListener { result ->
                jobList = result.documents.map { document ->
                    document.toObject(Job::class.java)!!.copy(id = document.id)
                }
            }
    }
}
