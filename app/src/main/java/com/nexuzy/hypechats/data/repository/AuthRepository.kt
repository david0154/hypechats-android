package com.nexuzy.hypechats.data.repository

import com.nexuzy.hypechats.data.api.apiService
import com.nexuzy.hypechats.data.local.TokenManager
import com.nexuzy.hypechats.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val tokenManager: TokenManager
) {
    
    suspend fun login(request: LoginRequest): ApiResponse<LoginResponse> {
        return try {
            val response = apiService.login(request)
            if (response.isSuccessful() && response.data != null) {
                // Save token and user ID
                tokenManager.saveToken(response.data.accessToken)
                tokenManager.saveUserId(response.data.userId)
                tokenManager.saveUsername(request.username)
                response
            } else {
                response
            }
        } catch (e: Exception) {
            ApiResponse<LoginResponse>(500, ApiError(500, e.message ?: "Unknown error"))
        }
    }
    
    suspend fun signup(request: SignupRequest): ApiResponse<LoginResponse> {
        return try {
            val response = apiService.signup(request)
            if (response.isSuccessful() && response.data != null) {
                // Save token and user ID
                tokenManager.saveToken(response.data.accessToken)
                tokenManager.saveUserId(response.data.userId)
                tokenManager.saveUsername(request.username)
                tokenManager.saveEmail(request.email)
                response
            } else {
                response
            }
        } catch (e: Exception) {
            ApiResponse<LoginResponse>(500, ApiError(500, e.message ?: "Unknown error"))
        }
    }
    
    suspend fun socialLogin(request: SocialLoginRequest): ApiResponse<LoginResponse> {
        return try {
            val response = apiService.socialLogin(request)
            if (response.isSuccessful() && response.data != null) {
                // Save token and user ID
                tokenManager.saveToken(response.data.accessToken)
                tokenManager.saveUserId(response.data.userId)
                response
            } else {
                response
            }
        } catch (e: Exception) {
            ApiResponse<LoginResponse>(500, ApiError(500, e.message ?: "Unknown error"))
        }
    }
    
    suspend fun logout(accessToken: String): ApiResponse<Unit> {
        return try {
            val response = apiService.logout(accessToken)
            if (response.isSuccessful()) {
                // Clear tokens
                tokenManager.clearTokens()
            }
            response
        } catch (e: Exception) {
            // Clear anyway
            tokenManager.clearTokens()
            ApiResponse<Unit>(500, ApiError(500, e.message ?: "Unknown error"))
        }
    }
    
    fun saveAccessToken(token: String) {
        tokenManager.saveToken(token)
    }
    
    fun saveUserId(userId: Int) {
        tokenManager.saveUserId(userId)
    }
    
    fun getAccessToken(): String = tokenManager.getToken()
    
    fun getUserId(): Int = tokenManager.getUserId()
    
    fun clearTokens() {
        tokenManager.clearTokens()
    }
    
    fun isLoggedIn(): Boolean = tokenManager.isLoggedIn()
}
