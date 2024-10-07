package com.kamau.ist.feature.profile

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kamau.ist.model.AppUser
import com.kamau.ist.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: FirestoreRepository,
    application: Application
) : AndroidViewModel(application) {

    private val storageReference = FirebaseStorage.getInstance().reference

    var currentUser: AppUser? = null
        private set

    // Function to open gallery and select an image
    fun openGalleryForImageSelection() {
        // This is just a placeholder function.
        // In your Activity or Fragment, use an Intent to open the gallery.
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        // Start activity with the intent and handle the result in your Activity/Fragment
    }

    // Function to upload profile picture to Firebase Storage and update the Firestore document
    fun uploadProfilePicture(imageUri: Uri, onUploadSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val fileRef = storageReference.child("profile_pictures/${currentUser?.uid}.jpg")

        fileRef.putFile(imageUri)
            .addOnSuccessListener {
                // Get the download URL of the uploaded image
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    // Update the user's profile picture URL in Firestore
                    currentUser?.let { user ->
                        user.profilePictureUrl = downloadUrl
                        updateUserProfile(user) { success ->
                            if (success) {
                                onUploadSuccess(downloadUrl)
                            } else {
                                onFailure(Exception("Failed to update profile in Firestore"))
                            }
                        }
                    }
                }.addOnFailureListener { exception ->
                    onFailure(exception)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Function to save profile details to Firestore
    fun saveProfile(name: String, email: String, workplace: String, profilePicture: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val updatedUser = currentUser?.copy(
                name = name,
                email = email,
                workplace = workplace,
                profilePictureUrl = profilePicture
            )

            updatedUser?.let {
                val result = repository.updateUserProfile(it)
                onComplete(result.isSuccess)
            } ?: onComplete(false)
        }
    }

    // Function to fetch user profile from Firestore
    fun fetchUserProfile(userId: String, onComplete: (AppUser?) -> Unit) {
        viewModelScope.launch {
            val result = repository.getUserProfile(userId)
            if (result.isSuccess) {
                currentUser = result.getOrNull()
                onComplete(currentUser)
            } else {
                onComplete(null)
            }
        }
    }

    // Function to update the user profile in Firestore
    private fun updateUserProfile(user: AppUser, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.updateUserProfile(user)
            onComplete(result.isSuccess)
        }
    }
}
