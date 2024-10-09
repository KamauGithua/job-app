package com.kamau.ist.feature

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kamau.ist.feature.job.JobViewModel
import com.kamau.ist.model.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveJobListScreen(viewModel: JobViewModel = hiltViewModel()) {
    // State to store the list of saved jobs
    var savedJobs by remember { mutableStateOf<List<Job>>(emptyList()) }

    // Fetch jobs from Firestore when the screen is composed
    LaunchedEffect(Unit) {
        viewModel.getSavedJobs { jobs ->
            savedJobs = jobs
        }
    }

    // Display the list of saved jobs
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        savedJobs.forEach { job ->
            Text(text = job.title,
                style = MaterialTheme.typography.titleLarge)
            Text(text = "Company: ${job.company}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Salary: ${job.salaryRange}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
