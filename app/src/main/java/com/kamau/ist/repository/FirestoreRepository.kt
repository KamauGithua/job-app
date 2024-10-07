package com.kamau.ist.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.kamau.ist.model.Job
import com.kamau.ist.model.AppUser // Updated to use AppUser

import com.kamau.ist.model.JobApplication
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {


    private val jobsCollection = firestore.collection("jobs")
    private val applicationsCollection = firestore.collection("applications")
    private val usersCollection = firestore.collection("users")


    fun getUserRole(userId: String, onComplete: (String?) -> Unit) {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val role = document.getString("role")
                    onComplete(role)
                } else {
                    onComplete(null)
                }
            }
            .addOnFailureListener {
                onComplete(null)
            }
    }
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

    //User profile functions

    suspend fun getUserProfile(userId: String): Result<AppUser> {
        return try {
            val document = usersCollection.document(userId).get().await()
            val user = document.toObject(AppUser::class.java) ?: AppUser()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserProfile(user: AppUser): Result<Void?> {
        return try {
            // Create a map to store user data, including the new 'workplace' field
            val userMap = mapOf(
                "uid" to user.uid,
                "name" to user.name,
                "email" to user.email,
                "workplace" to user.workplace,  // Include the workplace field
                "profilePictureUrl" to user.profilePictureUrl
            )
// Save the userMap to Firestore
            usersCollection.document(user.uid).set(userMap).await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfilePicture(userId: String, newProfilePictureUrl: String): Result<Void?> {
        return try {
            usersCollection.document(userId).update("profilePictureUrl", newProfilePictureUrl).await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

