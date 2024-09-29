package com.kamau.ist.feature.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kamau.ist.feature.auth.signup.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
) : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    // Holds the user role
    var userRole by mutableStateOf("")


    private val _navigationEvent = MutableStateFlow<SignInNavigationEvent?>(null)
    val navigationEvent = _navigationEvent.asStateFlow()


    fun signIn(email: String, password: String) {
        _state.value = SignInState.Loading
        // Firebase signIn
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let { user ->

                        firestore.collection("users").document(user.uid).get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val role = document.getString("role") ?: "User"
                                    userRole = role
                                    _state.value = SignInState.Success
                                    navigateToRoleBasedScreen(role)
                                } else {
                                    _state.value = SignInState.Error
                                    errorMessage = "User data not found"
                                }
                            }
                            .addOnFailureListener {
                                _state.value = SignInState.Error
                                errorMessage = "Failed to fetch user role"
                            }
                    }

                } else {
                    _state.value = SignInState.Error
//                    errorMessage = task.exception?.message ?: "Unknown error occurred"
                    errorMessage =  "Failed to sign in"
                }
            }
    }




    private fun navigateToRoleBasedScreen(role: String) {
        _navigationEvent.value = when (role) {
            "Admin" -> SignInNavigationEvent.AdminDashboard
            "User" -> SignInNavigationEvent.JobList
            else -> SignInNavigationEvent.JobList
        }
    }
        private fun fetchUserRole(uid: String) {
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val role = document.getString("role") ?: "User" // Default role is "User"
                        userRole = role
                        _state.value = SignInState.Success

                        navigateToRoleBasedScreen(role )
                    } else {
                        _state.value = SignInState.Error
                        errorMessage = "User role not found in database"
                    }
                }
                .addOnFailureListener { exception ->
                    _state.value = SignInState.Error
                    errorMessage = exception.message ?: "Failed to fetch user role"
                }
        }



    }

sealed class SignInNavigationEvent {
    object AdminDashboard : SignInNavigationEvent()
    object JobList : SignInNavigationEvent()
}


sealed class SignInState {
    object Nothing : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    object Error : SignInState()
}