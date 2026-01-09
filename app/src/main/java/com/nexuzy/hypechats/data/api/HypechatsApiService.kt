package com.nexuzy.hypechats.data.api

import retrofit2.http.*
import com.nexuzy.hypechats.data.model.*

interface HypechatsApiService {
    
    // ==================== AUTHENTICATION ====================
    
    @POST("user_authentication/login")
    suspend fun login(
        @Body request: LoginRequest
    ): ApiResponse<LoginResponse>
    
    @POST("user_authentication/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): ApiResponse<LoginResponse>
    
    @POST("user_authentication/social_login")
    suspend fun socialLogin(
        @Body request: SocialLoginRequest
    ): ApiResponse<LoginResponse>
    
    @POST("user_authentication/logout")
    suspend fun logout(
        @Header("Authorization") accessToken: String
    ): ApiResponse<Unit>
    
    // ==================== USER MANAGEMENT ====================
    
    @POST("user/data")
    suspend fun getUserData(
        @Header("Authorization") accessToken: String,
        @Body request: GetUserRequest
    ): ApiResponse<UserDataResponse>
    
    @POST("user/update")
    suspend fun updateUser(
        @Header("Authorization") accessToken: String,
        @Body request: UpdateUserRequest
    ): ApiResponse<Unit>
    
    @POST("user/follow")
    suspend fun followUser(
        @Header("Authorization") accessToken: String,
        @Body request: FollowRequest
    ): ApiResponse<Unit>
    
    @POST("user/suggestions")
    suspend fun getUserSuggestions(
        @Header("Authorization") accessToken: String
    ): ApiResponse<SuggestionsResponse>
    
    // ==================== POSTS ====================
    
    @POST("post/data")
    suspend fun getPostData(
        @Header("Authorization") accessToken: String,
        @Body request: GetPostRequest
    ): ApiResponse<PostDataResponse>
    
    @POST("post/action")
    suspend fun postAction(
        @Header("Authorization") accessToken: String,
        @Body request: PostActionRequest
    ): ApiResponse<Unit>
    
    @POST("post/create")
    suspend fun createPost(
        @Header("Authorization") accessToken: String,
        @Body request: CreatePostRequest
    ): ApiResponse<Unit>
    
    // ==================== SEARCH ====================
    
    @POST("search/users")
    suspend fun search(
        @Header("Authorization") accessToken: String,
        @Body request: SearchRequest
    ): ApiResponse<SearchResponse>
}
