package com.kamau.ist.feature.auth.signin


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kamau.ist.R
import com.kamau.ist.feature.auth.signup.SignUpState
import com.kamau.ist.feature.auth.signup.SignUpViewModel

@Composable
fun SignInScreen(navController: NavController){
    val viewModel: SignInViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    val navigationEvent by viewModel.navigationEvent.collectAsState()

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val userRole = viewModel.userRole

    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { event ->
            when (event) {
                SignInNavigationEvent.AdminDashboard -> navController.navigate("admin_dashboard")
                SignInNavigationEvent.JobList -> navController.navigate("job_list")
            }
        }
    }
    LaunchedEffect(key1 = uiState.value) {

        when (uiState.value) {
            is SignInState.Success -> {
                //Navigate based on the role
                when (userRole) {
                    "Admin" -> navController.navigate("admin_dashboard")
                    "User" -> navController.navigate("job_list")
                    else -> navController.navigate("job_list")
                }

            }

            is SignInState.Error -> {
                Toast.makeText(context, "Sign In failed: ${viewModel.errorMessage}", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }
    Scaffold(modifier = Modifier
        .fillMaxSize())
    { it ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(it)
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.ist), contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
            )
            OutlinedTextField(value = email,
                onValueChange ={ email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Email")})
            Spacer(modifier = Modifier.size(16.dp))

            OutlinedTextField(value = password,
                onValueChange ={password = it },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Password")}
            )
            Spacer(modifier = Modifier.size(16.dp))

            if (uiState.value == SignInState.Loading){
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { viewModel.signIn(email, password) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = email.isNotEmpty() && password.isNotEmpty() && (uiState.value == SignInState.Nothing || uiState.value == SignInState.Error)
                ) {
                    Text(text = "Sign In")
                }
                TextButton(onClick = { navController.navigate("signup") }) {
                    Text(text = "Don't have an account? Sign Up")
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen(){
    SignInScreen(navController = rememberNavController())
}