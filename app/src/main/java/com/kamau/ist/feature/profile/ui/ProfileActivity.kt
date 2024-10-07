package com.kamau.ist.feature.profile.ui

//class ProfileActivity {
//}

//package com.kamau.ist.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
//import androidx.compose.runtime.changelist.Operation.AdvanceSlotsBy.name
import androidx.navigation.compose.rememberNavController
import com.kamau.ist.feature.profile.ProfileScreen
import com.kamau.ist.feature.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()

    // Define user data
    private var name: String = ""
    private var email: String = ""
    private var workplace: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            // Your Composable ProfileScreen goes here
            ProfileScreen(navController = navController, profileViewModel = profileViewModel)
//
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data
            if (selectedImageUri != null) {
                // Upload the image and update the profile picture URL
                profileViewModel.uploadProfilePicture(
                    imageUri = selectedImageUri,
                    onUploadSuccess =  { imageUrl ->
                    // Assume you have variables 'name', 'email', and 'workplace' already defined
                    profileViewModel.saveProfile(
//                        name, email, workplace, imageUrl)
                        name = name,
                        email = email,
                        workplace = workplace,
                        profilePicture = imageUrl,
                        onComplete = {
                            println("Profile saved successfully")
                        }
                    )


                },
                    onComplete = {
                        // Perform any actions after upload completes
                        println("Upload completed successfully!")
                    },
                            onFailure = { exception ->
                        // Handle the failure case
                        Toast.makeText(this, "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
                )

            }
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
    }
}
