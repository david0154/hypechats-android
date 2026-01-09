package com.nexuzy.hypechats.ui.viewmodel

import androidx.lifecycle.*
import com.nexuzy.hypechats.data.repository.UserRepository
import com.nexuzy.hypechats.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    // ==================== UI STATE ====================
    
    private val _userProfile = MutableLiveData<User?>(null)
    val userProfile: LiveData<User?> = _userProfile
    
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error
    
    private val _updateSuccess = MutableLiveData(false)
    val updateSuccess: LiveData<Boolean> = _updateSuccess
    
    // ==================== LOAD PROFILE ====================
    
    fun loadUserProfile(userId: Int) = viewModelScope.launch {
        try {
            _isLoading.value = true
            _error.value = null
            
            val user = userRepository.getUserData(userId)
            _userProfile.value = user
        } catch (e: Exception) {
            _error.value = e.message ?: "Failed to load profile"
        } finally {
            _isLoading.value = false
        }
    }
    
    fun loadCurrentUserProfile() = viewModelScope.launch {
        try {
            _isLoading.value = true
            _error.value = null
            
            val user = userRepository.getCurrentUserData()
            _userProfile.value = user
        } catch (e: Exception) {
            _error.value = e.message ?: "Failed to load profile"
        } finally {
            _isLoading.value = false
        }
    }
    
    // ==================== UPDATE PROFILE ====================
    
    fun updateProfile(
        firstName: String? = null,
        lastName: String? = null,
        bio: String? = null,
        gender: String? = null,
        location: String? = null,
        website: String? = null
    ) = viewModelScope.launch {
        try {
            _isLoading.value = true
            _error.value = null
            
            val success = userRepository.updateUserData(
                firstName = firstName,
                lastName = lastName,
                bio = bio,
                gender = gender,
                location = location,
                website = website
            )
            
            if (success) {
                val currentProfile = _userProfile.value
                if (currentProfile != null) {
                    _userProfile.value = currentProfile.copy(
                        firstName = firstName ?: currentProfile.firstName,
                        lastName = lastName ?: currentProfile.lastName,
                        bio = bio ?: currentProfile.bio,
                        gender = gender ?: currentProfile.gender,
                        location = location ?: currentProfile.location,
                        website = website ?: currentProfile.website
                    )
                }
                _updateSuccess.value = true
            } else {
                _error.value = "Failed to update profile"
            }
        } catch (e: Exception) {
            _error.value = e.message ?: "Update failed"
        } finally {
            _isLoading.value = false
        }
    }
    
    // ==================== FOLLOW/UNFOLLOW ====================
    
    fun followUser(userId: Int) = viewModelScope.launch {
        try {
            val result = userRepository.followUser(userId)
            if (result) {
                val current = _userProfile.value
                if (current != null) {
                    _userProfile.value = current.copy(
                        followersCount = current.followersCount + 1
                    )
                }
            }
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
    
    fun unfollowUser(userId: Int) = viewModelScope.launch {
        try {
            val result = userRepository.unfollowUser(userId)
            if (result) {
                val current = _userProfile.value
                if (current != null) {
                    _userProfile.value = current.copy(
                        followersCount = maxOf(0, current.followersCount - 1)
                    )
                }
            }
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
    
    // ==================== UTILITY ====================
    
    fun clearError() {
        _error.value = null
    }
    
    fun clearUpdateSuccess() {
        _updateSuccess.value = false
    }
}
