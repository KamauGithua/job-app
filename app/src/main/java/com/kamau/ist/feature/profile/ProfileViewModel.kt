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

    // Function to open gallery and select image
    fun openGalleryForImageSelection() {
        // This is just a placeholder, use an Intent to open the gallery
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        // Trigger this intent in your activity and handle the result
    }

    // Function to save profile details
    fun saveProfile(name: String, email: String, workplace: String, profilePicture: String) {
        viewModelScope.launch {
            val updatedUser = currentUser?.copy(
                name = name,
                email = email,
                profilePictureUrl = profilePicture
            )

            if (updatedUser != null) {
                repository.updateUserProfile(updatedUser)
            }
        }
    }

    // Function to upload profile picture to Firebase Storage
    fun uploadProfilePicture(imageUri: Uri, onSuccess: (String) -> Unit) {
        val fileRef = storageReference.child("profile_pictures/${currentUser?.uid}.jpg")
        fileRef.putFile(imageUri)
            .addOnSuccessListener {
                // Get the download URL
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString()) // Return the URL of the uploaded image
                }
            }
            .addOnFailureListener { e ->
                // Handle errors here
                println("Error uploading image: ${e.message}")
            }
    }
}
