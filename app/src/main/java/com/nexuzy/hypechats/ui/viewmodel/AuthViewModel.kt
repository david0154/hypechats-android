package com.nexuzy.hypechats.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.nexuzy.hypechats.data.repository.AuthRepository
import com.nexuzy.hypechats.data.model.LoginRequest
import com.nexuzy.hypechats.data.model.SignupRequest
import com.nexuzy.hypechats.data.model.SocialLoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {
    
    // ==================== UI STATE ====================
    
    private val _authState = MutableLiveData<AuthState>(AuthState.Idle)
    val authState: LiveData<AuthState> = _authState
    
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error
    
    private val _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn
    
    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        object LoginSuccess : AuthState()
        object SignupSuccess : AuthState()
        object LogoutSuccess : AuthState()
        data class Error(val message: String) : AuthState()
    }
    
    init {
        checkIfUserLoggedIn()
    }
    
    // ==================== LOGIN ====================
    
    fun login(username: String, password: String) = viewModelScope.launch {
        if (username.isEmpty() || password.isEmpty()) {
            _error.value = "Username and password required"
            _authState.value = AuthState.Error("Validation error")
            return@launch
        }
        
        try {
            _isLoading.value = true
            _authState.value = AuthState.Loading
            _error.value = null
            
            val result = authRepository.login(
                LoginRequest(
                    username = username.trim(),
                    password = password
                )
            )
            
            if (result.isSuccessful()) {
                _authState.value = AuthState.LoginSuccess
                _isLoggedIn.value = true
            } else {
                val errorMsg = result.errors?.errorText ?: "Login failed"
                _error.value = errorMsg
                _authState.value = AuthState.Error(errorMsg)
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Unknown error occurred"
            _error.value = errorMsg
            _authState.value = AuthState.Error(errorMsg)
        } finally {
            _isLoading.value = false
        }
    }
    
    // ==================== SIGNUP ====================
    
    fun signup(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) = viewModelScope.launch {
        try {
            _isLoading.value = true
            _authState.value = AuthState.Loading
            _error.value = null
            
            // Validation
            when {
                username.isEmpty() || email.isEmpty() || password.isEmpty() -> {
                    _error.value = "All fields are required"
                    _authState.value = AuthState.Error("Validation error")
                    return@launch
                }
                username.length < 3 -> {
                    _error.value = "Username must be at least 3 characters"
                    _authState.value = AuthState.Error("Validation error")
                    return@launch
                }
                !isValidEmail(email) -> {
                    _error.value = "Please enter a valid email"
                    _authState.value = AuthState.Error("Validation error")
                    return@launch
                }
                password.length < 6 -> {
                    _error.value = "Password must be at least 6 characters"
                    _authState.value = AuthState.Error("Validation error")
                    return@launch
                }
                password != confirmPassword -> {
                    _error.value = "Passwords do not match"
                    _authState.value = AuthState.Error("Validation error")
                    return@launch
                }
            }
            
            val result = authRepository.signup(
                SignupRequest(
                    username = username.trim(),
                    email = email.trim(),
                    password = password,
                    confirmPassword = confirmPassword
                )
            )
            
            if (result.isSuccessful()) {
                _authState.value = AuthState.SignupSuccess
                _isLoggedIn.value = true
            } else {
                val errorMsg = result.errors?.errorText ?: "Signup failed"
                _error.value = errorMsg
                _authState.value = AuthState.Error(errorMsg)
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Unknown error occurred"
            _error.value = errorMsg
            _authState.value = AuthState.Error(errorMsg)
        } finally {
            _isLoading.value = false
        }
    }
    
    // ==================== SOCIAL LOGIN ====================
    
    fun socialLogin(
        accessToken: String,
        provider: String,
        googleKey: String? = null
    ) = viewModelScope.launch {
        try {
            _isLoading.value = true
            _authState.value = AuthState.Loading
            _error.value = null
            
            val result = authRepository.socialLogin(
                SocialLoginRequest(
                    accessToken = accessToken,
                    provider = provider,
                    googleKey = googleKey
                )
            )
            
            if (result.isSuccessful()) {
                _authState.value = AuthState.LoginSuccess
                _isLoggedIn.value = true
            } else {
                val errorMsg = result.errors?.errorText ?: "Social login failed"
                _error.value = errorMsg
                _authState.value = AuthState.Error(errorMsg)
            }
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Unknown error occurred"
            _error.value = errorMsg
            _authState.value = AuthState.Error(errorMsg)
        } finally {
            _isLoading.value = false
        }
    }
    
    // ==================== LOGOUT ====================
    
    fun logout() = viewModelScope.launch {
        try {
            _isLoading.value = true
            val accessToken = authRepository.getAccessToken()
            
            if (accessToken.isNotEmpty()) {
                authRepository.logout(accessToken)
            }
            
            _authState.value = AuthState.LogoutSuccess
            _isLoggedIn.value = false
        } catch (e: Exception) {
            authRepository.clearTokens()
            _isLoggedIn.value = false
        } finally {
            _isLoading.value = false
        }
    }
    
    // ==================== UTILITY ====================
    
    fun checkIfUserLoggedIn() {
        _isLoggedIn.value = authRepository.isLoggedIn()
    }
    
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun clearError() {
        _error.value = null
    }
}
