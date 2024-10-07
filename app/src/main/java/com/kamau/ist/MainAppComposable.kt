package com.kamau.ist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kamau.ist.feature.admin.AdminDashboardScreen
import com.kamau.ist.feature.admin.PostJobScreen
import com.kamau.ist.feature.application.ApplicationFormScreen
import com.kamau.ist.feature.auth.signin.SignInScreen
import com.kamau.ist.feature.auth.signup.SignUpScreen
import com.kamau.ist.feature.home.HomeScreen
import com.kamau.ist.feature.job.JobDetailScreen
import com.kamau.ist.feature.job.JobListScreen
import com.kamau.ist.feature.profile.ProfileScreen

@Composable
fun MainApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()


        var startDestination by remember { mutableStateOf("login") }

        // Firestore instance
        val firestore = FirebaseFirestore.getInstance()

        // Check Firestore for the current user
        LaunchedEffect(Unit) {
            val userId = "CURRENT_USER_ID" // Replace this with how you get the logged-in user's ID

            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val role = document.getString("role") ?: "User"
                        startDestination = if (role == "Admin") {
                            "admin_dashboard"
                        } else {
                            "job_list"
                        }
                    } else {
                        startDestination = "login"
                    }
                }
                .addOnFailureListener {
                    startDestination = "login" // Handle errors by defaulting to login
                }
        }




        NavHost(navController = navController, startDestination = startDestination) {

            composable("login") {
                SignInScreen(navController)
            }
            composable("signup") {
                SignUpScreen(navController)
            }
            composable("home") {
                HomeScreen(navController)
            }
            composable("profile") {
                ProfileScreen(navController)
            }
//            composable("signup") {
//                SignUpScreen(navController)
//            }
            composable("post_job") {
                PostJobScreen(navController)
            }

            composable("job_list") { JobListScreen(navController) }
            composable("job_detail/{jobId}") { backStackEntry ->
                val jobId = backStackEntry.arguments?.getString("jobId")
                JobDetailScreen(navController, jobId)
            }
            composable("application_form/{jobId}") { backStackEntry ->
                val jobId = backStackEntry.arguments?.getString("jobId")
                ApplicationFormScreen(navController, jobId)
            }
            composable("admin_dashboard") {
                // Add your AdminDashboardScreen here
                AdminDashboardScreen(navController)
            }
        }
    }
}
