package com.kamau.ist.feature.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val navController: NavController
) : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    private var errorMessage by mutableStateOf("")

    // Holds the user role
    private var userRole by mutableStateOf("")




    fun signIn(email: String, password: String) {
        _state.value = SignInState.Loading
        // Firebase signIn
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        _state.value = SignInState.Success
                        return@addOnCompleteListener
                    }
                    _state.value = SignInState.Error

                } else {
                    _state.value = SignInState.Error
                }
            }
        fun navigateToRoleBasedScreen(role: String) {
            when (role) {
                "Admin" -> navController.navigate("admin_dashboard")
                "User" -> navController.navigate("job_list")
            }
        }

        // Fetch user role from Firestore
        fun fetchUserRole(uid: String) {
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val role = document.getString("role") ?: "User" // Default role is "User"
                        userRole = role
                        navigateToRoleBasedScreen(role)
                    }
                }
                .addOnFailureListener {
                    errorMessage = "Failed to fetch user role"
                }
        }
    }
}

sealed class SignInState {
    object Nothing : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    object Error : SignInState()
}