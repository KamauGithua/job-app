package com.kamau.ist.feature.job

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(navController: NavController, jobId: String?, viewModel: JobViewModel = hiltViewModel()) {
    val job = viewModel.jobList.find { it.id == jobId }

    if (job != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(job.title) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            content = {
                Column(modifier = Modifier.padding(it).padding(16.dp)) {
                    Text(text = job.description)
                    Text(text = "Company: ${job.company}")
                    Text(text = "Requirements: ${job.requirements}")
                    Text(text = "Deadline: ${job.deadline}")

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { navController.navigate("application_form/$jobId") }) {
                        Text(text = "Apply Now")
                    }
                    Button(onClick = {
                        }) {
                        Text(text = "Save")
                    }
                }
            }
        )
    }
}
