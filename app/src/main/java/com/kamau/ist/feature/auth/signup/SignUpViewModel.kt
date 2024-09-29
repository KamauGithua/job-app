package com.kamau.ist.feature.auth.signup

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
class SignUpViewModel @Inject constructor(
//    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
//    private val navController: NavController
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    val state = _state.asStateFlow()

    // State for handling navigation events
    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent = _navigationEvent.asStateFlow()


    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    private var errorMessage by mutableStateOf("")




    fun signUp(name: String, email: String, password: String) {
        _state.value = SignUpState.Loading
        // Firebase signIn
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {user ->
                        val userData = hashMapOf(
                            "email" to email,
                            "role" to "User" // Default role is User
                        )

                        // Save user data in Firestore
                        firestore.collection("users").document(user.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                _state.value = SignUpState.Success
                                navigateToRoleBasedScreen("User")
                            }
                            .addOnFailureListener {
                                _state.value = SignUpState.Error
                                errorMessage = "Failed to save user data"
                            }
                    }

                } else {
                    _state.value = SignUpState.Error
                }
            }
    }
    // Navigate to role-based screens
    private fun navigateToRoleBasedScreen(role: String) {
        when (role) {
            "Admin" -> _navigationEvent.value = NavigationEvent.NavigateToAdminDashboard
            "User" -> _navigationEvent.value = NavigationEvent.NavigateToJobList
        }
    }

    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }

}

// Represent different navigation events
sealed class NavigationEvent {
    object NavigateToAdminDashboard : NavigationEvent()
    object NavigateToJobList : NavigationEvent()
    object NavigateToSignIn : NavigationEvent()
}

sealed class SignUpState {
    object Nothing : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    object Error : SignUpState()
}