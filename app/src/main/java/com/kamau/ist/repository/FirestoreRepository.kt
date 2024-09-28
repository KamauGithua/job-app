package com.kamau.ist.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.kamau.ist.model.Job
import com.kamau.ist.model.JobApplication
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    // Reference to Firestore database
    private val firestore = FirebaseFirestore.getInstance()

    // Reference to the 'Jobs' collection
    private val jobsCollection = firestore.collection("Jobs")

    // Reference to the 'Applications' collection
    private val applicationsCollection = firestore.collection("Applications")

    // Function to add a new job posting to Firestore
    suspend fun addJob(job: Job): Result<Void?> {
        return try {
            // Auto-generate a new document ID for the job
            val jobId = jobsCollection.document().id
            job.id = jobId // Set the document ID to the job object
            jobsCollection.document(jobId).set(job).await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Function to fetch all job listings
    suspend fun getJobListings(): Result<List<Job>> {
        return try {
            val result = jobsCollection.get().await()
            val jobList = result.toObjects(Job::class.java)
            Result.success(jobList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Function to submit a new job application
    suspend fun submitJobApplication(application: Application): Result<Void?> {
        return try {
            val applicationId = applicationsCollection.document().id
            application.id = applicationId // Set the document ID to the application object
            applicationsCollection.document(applicationId).set(application).await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
