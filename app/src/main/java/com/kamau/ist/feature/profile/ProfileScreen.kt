package com.kamau.ist.feature.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.kamau.ist.R

@Composable
fun ProfileScreen(navController: NavHostController, profileViewModel: ProfileViewModel) {
    var name by remember { mutableStateOf("Kamau") }
    var email by remember { mutableStateOf("kamauleakey99@gmail.com") }
    var workplace by remember { mutableStateOf("GOOGLE") }
    var profilePicture by remember { mutableStateOf("https://example.com/default_profile_pic.png") }  // Default profile picture URL

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Profile Picture Section
        Image(
            painter = rememberAsyncImagePainter(profilePicture),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        // Edit Button to change profile picture
        IconButton(onClick = {
            // Open the gallery to select a profile picture
            profileViewModel.openGalleryForImageSelection()
        }) {
            Icon(
                Icons.Rounded.Add,
//                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit Profile Picture"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // User's Name and Email
        Text(text = name, fontSize = 24.sp, style = MaterialTheme.typography.bodyMedium)
        Text(text = email, fontSize = 16.sp, color = Color.Gray)

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
                // Update the profile in Firestore
                profileViewModel.saveProfile(name, email, workplace, profilePicture)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Back to Main Page Button
        Button(
            onClick = {
                navController.navigate("profile")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Back to Main Page")
        }
    }
}


