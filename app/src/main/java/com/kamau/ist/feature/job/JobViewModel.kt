package com.kamau.ist.feature.job

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.kamau.ist.model.Job
import com.kamau.ist.model.JobApplication
import com.kamau.ist.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(

    private val repository: FirestoreRepository
//    private val firestore: FirebaseFirestore
) : ViewModel() {


    var jobList by mutableStateOf(listOf<Job>())
        private set

    var applicationStatus by mutableStateOf<Result<Void?>>(Result.success(null))
        private set

    var savedJobCount by mutableStateOf(0)  // Mutable state to track number of saved jobs
        private set

    init {
        loadJobs()
    }

    // Fetch all job listings from Firestore
    private fun loadJobs() {
        viewModelScope.launch {
            val result = repository.getJobListings()
            result.onSuccess { jobs ->
                jobList = jobs
            }.onFailure { exception ->
                // Handle errors here
                println("Error loading jobs: ${exception.message}")
            }
        }
    }

    // Function to add a new job posting (Admin use case)
    fun addJob(job: Job) {
        viewModelScope.launch {
            repository.addJob(job)
        }
    }

    // Function to submit a new job application
    fun submitJobApplication(application: JobApplication) {
        viewModelScope.launch {
            val result = repository.submitJobApplication(application)
            applicationStatus = result
        }
    }

    // Function to save a job in Firestore
    fun saveJob(job: Job) {
        viewModelScope.launch {
            val result = repository.saveJob(job)
            result.onSuccess {
                savedJobCount++
                // Notify user of success (if needed)
            }.onFailure {
                // Handle failure
            }
        }
    }

    // Method to retrieve saved jobs
    fun getSavedJobs(onJobsLoaded: (List<Job>) -> Unit) {
        viewModelScope.launch {
            val result = repository.getSavedJobs()
            result.onSuccess { jobs ->
                onJobsLoaded(jobs)
            }.onFailure { exception ->
                // Handle failure (e.g., show error message)
                println("Error fetching saved jobs: ${exception.message}")
            }
        }
    }
}
