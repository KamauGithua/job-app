package com.kamau.ist.feature.application

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationFormScreen(navController: NavController, jobId: String?, viewModel: ApplicationViewModel = hiltViewModel()) {
    var coverLetter by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Application Form") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier.padding(it).padding(16.dp)) {
                TextField(
                    value = coverLetter,
                    onValueChange = { coverLetter = it },
                    label = { Text(text = "Cover Letter")},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = skills,
                    onValueChange = { skills = it },
                    label = { Text(text = "Skills")},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        viewModel.applyForJob(userId, jobId ?: "", coverLetter)
                        navController.navigate("job_list")
                    }
                ) {
                    Text("Submit Application")
                }
            }
        }
    )
}
