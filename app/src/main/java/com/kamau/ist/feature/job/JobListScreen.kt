package com.kamau.ist.feature.job

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kamau.ist.model.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListScreen(navController: NavController, viewModel: JobViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar
        TopBar(navController)

        // Search Bar
        SearchBar()

        // Suggested Jobs Section
        SuggestedJobsSection(navController)

        // Recent Jobs Section
//        RecentJobsSection(navController)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {

    TopAppBar(title = { Text("Job Listings", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Red),
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("home")
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Search Jobs") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}


@Composable
fun SuggestedJobsSection( navController : NavController, viewModel: JobViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Suggested Jobs", fontWeight = FontWeight.Bold)
            TextButton(
                onClick = { /*TODO: Navigate to See All*/ }
            ) {
                Text(text = "See All", color = Color.Red)
            }
        }

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            items(viewModel.jobList) { job ->
                JobItem(
                    job = job,
                    title = job.title,
                    company = job.company,
                    location = job.location,
                    jobTypes = job.jobTypes,
                    salaryRange = job.salaryRange,
                    navController = navController,
                    onClick = {
                        navController.navigate("job_detail/${job.id}")
                    }
                )
            }
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun JobItem(job: Job, onClick: () -> Unit,
            title: String,
    company: String,
    location: String,
    jobTypes: String,
    salaryRange: String,
    navController: NavController
    ) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                ) {
                Text(text = job.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = job.company, color = Color.Gray)
//                Spacer(modifier = Modifier.height(4.dp))
                Text(text =  job.location, color = Color.Gray)
//                Spacer(modifier = Modifier.height(4.dp))
                Text(text = job.jobTypes, color = Color.Red)
//                Spacer(modifier = Modifier.height(4.dp))
                Text(text = job.salaryRange, color = Color.Red)
//                Spacer(modifier = Modifier.height(4.dp))
                Text(text = job.skills, color = Color.Gray)
//                Spacer(modifier = Modifier.height(4.dp))
                Text(text = job.deadline, color = Color.Gray)
Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                navController.navigate("jobDetail")
                             // Passing first job type as example
                //                navController.navigate("notifications") // Passing first job type as example
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                Text("View Job")
            }
//                Text(text = "Company: ${job.location}", color = Color.Gray)
//                Text(text = "Company: ${job.jobTypes}", color = Color.Gray)
//                Text(text = "Company: ${job.salaryRange}", color = Color.Gray)
//                Text(text = "Company: ${job.skills}", color = Color.Gray)
//                Text(text = "Deadline: ${job.deadline}", color = Color.Gray)
            }
        }
    }
}


@Composable
fun JobTypeChip(text: String) {
    Box(
        modifier = Modifier
            .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = Color.DarkGray, fontSize = 12.sp)
    }
}