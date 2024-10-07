package com.kamau.ist.feature.job

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import androidx.compose.material3.Switch
import com.kamau.ist.feature.auth.signin.SignInScreen
import com.kamau.ist.model.Job

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
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(job.companyLogoUrl), // Use actual URL
                        contentDescription = "Company Logo",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = job.company,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

// Second Row: Location and Application Date
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = job.location)
                    Text(text = "Posted: ${job.postedDate}")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Third Row: Job Category
                Text(text = "${job.category} • ${job.employmentType}")

                Spacer(modifier = Modifier.height(8.dp))

                // Fourth Row: Skills
                Text(
                    text = "Skills: ${job.skills.joinToString(", ")}",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fifth Row: Compare with other applicants
                Text(
                    text = "See how you compare to other applicants. Try Premium",
                    color = Color.Blue,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sixth Row: Apply and Save Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { /* Handle Apply Action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                    ) {
                        Text(text = "Apply", color = Color.White)
                    }
                    Button(
                        onClick = { /* Handle Save Action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text(text = "Save", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Seventh Row: About the Job
                Text(
                    text = "About the job",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = job.description)

                Spacer(modifier = Modifier.height(16.dp))

                // Qualifications Section
                Text(
                    text = "Qualifications",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = job.qualifications.joinToString("\n"))


                // Eighth Row: Set Alert for Similar Jobs
                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Set alert for similar jobs")
                    Spacer(modifier = Modifier.weight(1f))
                    TextField(
                        value = job.alertLocation,
                        onValueChange = { /* Handle Text Change */ },
                        label = { Text("Location") },
                        modifier = Modifier.width(200.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Switch(
                        checked = job.isAlertActive,
                        onCheckedChange = { /* Handle Toggle */ }
                    )
                }

                // Ninth Row: Job Skills
                Text(
                    text = "Skills",
//                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = job.skills.joinToString(", "),
                    modifier = Modifier.padding(top = 8.dp)
                )

                // Tenth Row: About the Job Company
                Text(
                    text = "About the Job Company",
//                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberImagePainter(data = job.companyLogoUrl),
                        contentDescription = "Company Logo",
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = job.companyName)
                        Button(onClick = { /* Handle Follow */ }) {
                            Text(text = "+ Follow")
                        }
                    }
                }
                Text(
                    text = job.smallCompanyDescription,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = job.bigCompanyDescription,
                    modifier = Modifier.padding(top = 16.dp)
                )


                // Eleventh Row: More Jobs
                Text(
                    text = "More Jobs",
//                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = 16.dp)
                )
                LazyColumn {

                    items(viewModel.jobList) { moreJob ->
                        MoreJobItem(moreJob)
//                    items(jobViewModel.moreJobListings) { moreJob ->
//                        MoreJobItem(moreJob)
                    }
                }
            }

        )
    }
}



//                Text(text = job.description)
//                    Text(text = "Company: ${job.company}")
//                    Text(text = "Requirements: ${job.requirements}")
//                    Text(text = "Deadline: ${job.deadline}")
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Button(onClick = { navController.navigate("application_form/$jobId") }) {
//                        Text(text = "Apply Now")
//                    }
//                    Button(onClick = {
//                        }) {
//                        Text(text = "Save")
//                    }




@Composable
fun MoreJobItem(job: Job) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = job.title)
        Text(text = "Company: ${job.companyName}")
        Text(text = "Location: ${job.location}")
        Text(text = "Category: ${job.category}")
    }
}




