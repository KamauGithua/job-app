package com.kamau.ist.feature.job

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kamau.ist.model.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(navController: NavController, jobId: String?, viewModel: JobViewModel = hiltViewModel()) {
    val job = viewModel.jobList.find { it.id == jobId }

    Scaffold(
        topBar = { JobDetailTopAppBar(navController) },
        content = {
            job?.let {
                // Pass job details to the AboutJobsSection to display
                AboutJobsSection(job = job, navController = navController)
            } ?: run {
                // Show a message when job is not found
                Box(modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                ) {
                    Text(text = "Job details not available", color = Color.Gray)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailTopAppBar(navController: NavController) {
    TopAppBar(
        title = { Text("Job Details", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Red),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun AboutJobsSection(job: Job, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "View Job", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                // Display job details using AboutJobItem
                AboutJobItem(job = job, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutJobItem(
    job: Job,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("job_detail/${job.id}") },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Job title and company
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = job.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = job.company,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Job location and deadline
            Text(
                text = "${job.location} • ${job.deadline}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Job type and salary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = job.jobTypes,
                    color = Color.Red
                )
                Text(
                    text = job.salaryRange,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Job description
            Text(
                text = job.description,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Apply Button
//            Button(
//                onClick = { navController.navigate("application_form/${job.id}") },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Apply Job")
//            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly, // Arrange buttons evenly across the row
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Save Job Button
                Button(
                    onClick = { navController.navigate("save_list")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray), // Set a different color for "Save Job" button if needed
                    modifier = Modifier.weight(1f) // Take up equal space in the row
                ) {
                    Text("Save Job")
                }

                Spacer(modifier = Modifier.width(16.dp)) // Spacer to add some space between buttons

                // Apply Job Button
                Button(
                    onClick = { navController.navigate("application_form/${job.id}") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.weight(1f) // Take up equal space in the row
                ) {
                    Text("Apply Job")
                }
            }

        }
    }
}
