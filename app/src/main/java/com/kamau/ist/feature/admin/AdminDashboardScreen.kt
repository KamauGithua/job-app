package com.kamau.ist.feature.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun AdminDashboardScreen(navController: NavController,
                         viewModel: AdminViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Admin Dashboard") })
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Text(text = "Welcome, Admin")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("post_job") }) {
                    Text(text = "Post New Job")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("job_list") }) {
                    Text(text = "View Job Listings")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.logoutAdmin {
                            navController.navigate("login") {
                                popUpTo("admin_dashboard") { inclusive = true } // Clear the back stack
                            }
                        }
                    }
                ) {
                    Text(text = "Logout")
                }
            }
        }
    )
}
