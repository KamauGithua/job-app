package com.kamau.ist.feature.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.kamau.ist.R

@Composable
fun ProfileScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("Kamau") }
    var email by remember { mutableStateOf("kamauleakey99@gmail.com") }
    var workplace by remember { mutableStateOf("GOOGLE") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Profile Picture Section
        Image(
            painter = painterResource(id = R.drawable.personal),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // User's Name
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
                // Handle save profile logic here
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


