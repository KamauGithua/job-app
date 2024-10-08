package com.kamau.ist.feature.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel = hiltViewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var workplace by remember { mutableStateOf("") }
    var profilePictureUri by remember { mutableStateOf<Uri?>(null) } // Holds the URI of the profile picture


    // Launcher to open gallery and get the image URI
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Call ViewModel function to upload the selected image
            profileViewModel.uploadProfilePicture(
                imageUri = it,
                onUploadSuccess = { imageUrl ->
                // After successful upload, update the profile picture URI
                profilePictureUri = Uri.parse(imageUrl.toString()) // Assume imageUrl is a String URL
            },
            onComplete = {
                    // Perform any actions after upload completes
                    println("Image upload completed successfully!")
                },
                onFailure = { exception ->
                    println("Failed to upload image: ${exception.message}")
                }
            )



        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Profile Picture Section
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            // Display the profile picture or a default person icon if no image is selected
            if (profilePictureUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = profilePictureUri),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            } else {
                // Default profile picture (e.g., person icon)
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Default Profile Picture",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }

            // Edit Button to change profile picture
            IconButton(
                onClick = {
                    // Open the gallery to select a profile picture
                    imagePickerLauncher.launch("image/*")
                },
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Edit Profile Picture",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // User's Name and Email
        Text(text = name.ifEmpty { "Name not set" }, fontSize = 24.sp, style = MaterialTheme.typography.bodyMedium)
        Text(text = email.ifEmpty { "Email not set" }, fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(30.dp))

        // Input fields to update profile
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = email,
            onValueChange = { email  = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = workplace,
            onValueChange = { workplace = it },
            label = { Text("Workplace") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Save Button
        Button(
            onClick = {
                // Update the profile in Firestore or any other backend service
                profileViewModel.saveProfile(
//                    name, email, workplace, profilePictureUri.toString())
                    name = name,
                    email = email,
                    workplace = workplace,
                    profilePicture = profilePictureUri.toString(),
                    onComplete = {
                        println("Profile saved successfully!")
                    }
                )

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Back to Main Page Button
        Button(
            onClick = {
                navController.navigate("home")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Back to Main Page")
        }
    }
}
