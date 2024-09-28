package com.kamau.ist.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.kamau.ist.model.Job
import com.kamau.ist.model.JobApplication
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val jobsCollection = firestore.collection("jobs")
    private val applicationsCollection = firestore.collection("applications")

    // Function to add a new job posting
    suspend fun addJob(job: Job): Result<Void?> {
        return try {
            val jobId = jobsCollection.document().id
            job.id = jobId
            jobsCollection.document(jobId).set(job).await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Function to fetch job listings
    suspend fun getJobListings(): Result<List<Job>> {
        return try {
            val result = jobsCollection.get().await()
            val jobs = result.toObjects(Job::class.java)
            Result.success(jobs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Function to submit a new job application
    suspend fun submitJobApplication(application: JobApplication): Result<Void?> {
        return try {
            val applicationId = applicationsCollection.document().id
            applicationsCollection.document(applicationId).set(application).await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

