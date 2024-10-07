package com.kamau.ist.feature.profile.ui

//class ProfileActivity {
//}

//package com.kamau.ist.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
//import androidx.compose.runtime.changelist.Operation.AdvanceSlotsBy.name
import androidx.lifecycle.viewmodel.compose.viewModel
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
                profileViewModel.uploadProfilePicture(selectedImageUri) { imageUrl ->
                    // Assume you have variables 'name', 'email', and 'workplace' already defined
                    profileViewModel.saveProfile(name, email, workplace, imageUrl)
                }
            }
        }
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 1001
    }
}
