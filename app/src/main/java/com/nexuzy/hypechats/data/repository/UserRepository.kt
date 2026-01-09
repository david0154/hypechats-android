package com.nexuzy.hypechats.data.repository

import com.nexuzy.hypechats.data.api.apiService
import com.nexuzy.hypechats.data.local.TokenManager
import com.nexuzy.hypechats.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val tokenManager: TokenManager
) {
    
    private val accessToken: String
        get() = tokenManager.getToken()
    
    // ==================== GET USER DATA ====================
    
    suspend fun getUserData(userId: Int): User? {
        return try {
            val response = apiService.getUserData(
                accessToken = accessToken,
                request = GetUserRequest(
                    userId = userId,
                    fetch = "user_data,followers,following"
                )
            )
            
            if (response.isSuccessful()) {
                response.data?.userData
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun getCurrentUserData(): User? {
        return try {
            val userId = tokenManager.getUserId()
            if (userId <= 0) return null
            getUserData(userId)
        } catch (e: Exception) {
            null
        }
    }
    
    // ==================== GET FOLLOWERS ====================
    
    suspend fun getFollowers(userId: Int): List<User> {
        return try {
            val response = apiService.getUserData(
                accessToken = accessToken,
                request = GetUserRequest(
                    userId = userId,
                    fetch = "followers"
                )
            )
            
            if (response.isSuccessful()) {
                response.data?.followers ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    // ==================== GET FOLLOWING ====================
    
    suspend fun getFollowing(userId: Int): List<User> {
        return try {
            val response = apiService.getUserData(
                accessToken = accessToken,
                request = GetUserRequest(
                    userId = userId,
                    fetch = "following"
                )
            )
            
            if (response.isSuccessful()) {
                response.data?.following ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    // ==================== UPDATE USER DATA ====================
    
    suspend fun updateUserData(
        firstName: String? = null,
        lastName: String? = null,
        bio: String? = null,
        gender: String? = null,
        location: String? = null,
        website: String? = null
    ): Boolean {
        return try {
            val request = UpdateUserRequest(
                firstName = firstName,
                lastName = lastName,
                bio = bio,
                gender = gender,
                location = location,
                website = website
            )
            
            val response = apiService.updateUser(
                accessToken = accessToken,
                request = request
            )
            
            response.isSuccessful()
        } catch (e: Exception) {
            false
        }
    }
    
    // ==================== FOLLOW USER ====================
    
    suspend fun followUser(userId: Int): Boolean {
        return try {
            val response = apiService.followUser(
                accessToken = accessToken,
                request = FollowRequest(userId = userId)
            )
            response.isSuccessful()
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun unfollowUser(userId: Int): Boolean {
        return try {
            val response = apiService.followUser(
                accessToken = accessToken,
                request = FollowRequest(userId = userId)
            )
            response.isSuccessful()
        } catch (e: Exception) {
            false
        }
    }
    
    // ==================== GET SUGGESTIONS ====================
    
    suspend fun getUserSuggestions(): List<User> {
        return try {
            val response = apiService.getUserSuggestions(
                accessToken = accessToken
            )
            
            if (response.isSuccessful()) {
                response.data?.suggestions ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    // ==================== SEARCH USERS ====================
    
    suspend fun searchUsers(query: String): List<User> {
        return try {
            val response = apiService.search(
                accessToken = accessToken,
                request = SearchRequest(searchKey = query)
            )
            
            if (response.isSuccessful()) {
                response.data?.users ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
