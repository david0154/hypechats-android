package com.nexuzy.hypechats.data.repository

import com.nexuzy.hypechats.data.api.apiService
import com.nexuzy.hypechats.data.local.TokenManager
import com.nexuzy.hypechats.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepository @Inject constructor(
    private val tokenManager: TokenManager
) {
    
    private val accessToken: String
        get() = tokenManager.getToken()
    
    // ==================== GET FEED ====================
    
    suspend fun getNewsFeed(offset: Int = 0): List<Post> {
        return try {
            val response = apiService.getPostData(
                accessToken = accessToken,
                request = GetPostRequest(
                    postId = 0,  // 0 means get feed
                    fetch = "post_data"
                )
            )
            
            if (response.isSuccessful()) {
                listOf(response.data?.postData).filterNotNull()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    // ==================== LIKE POST ====================
    
    suspend fun likePost(postId: Int): Boolean {
        return try {
            val response = apiService.postAction(
                accessToken = accessToken,
                request = PostActionRequest(
                    postId = postId,
                    action = "like"
                )
            )
            response.isSuccessful()
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun unlikePost(postId: Int): Boolean {
        return try {
            val response = apiService.postAction(
                accessToken = accessToken,
                request = PostActionRequest(
                    postId = postId,
                    action = "dislike"
                )
            )
            response.isSuccessful()
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun dislikePost(postId: Int): Boolean {
        return likePost(postId)  // Same action as like
    }
    
    suspend fun undislikePost(postId: Int): Boolean {
        return unlikePost(postId)  // Same action as unlike
    }
    
    // ==================== WONDER POST ====================
    
    suspend fun wonderPost(postId: Int): Boolean {
        return try {
            val response = apiService.postAction(
                accessToken = accessToken,
                request = PostActionRequest(
                    postId = postId,
                    action = "wonder"
                )
            )
            response.isSuccessful()
        } catch (e: Exception) {
            false
        }
    }
    
    // ==================== COMMENT POST ====================
    
    suspend fun addComment(postId: Int, commentText: String): Boolean {
        return try {
            if (commentText.trim().isEmpty()) return false
            
            val response = apiService.postAction(
                accessToken = accessToken,
                request = PostActionRequest(
                    postId = postId,
                    action = "comment",
                    text = commentText
                )
            )
            response.isSuccessful()
        } catch (e: Exception) {
            false
        }
    }
    
    // ==================== GET POST DATA ====================
    
    suspend fun getPostData(
        postId: Int,
        fetchType: String = "post_data,post_comments,post_liked_users"
    ): PostDataResponse? {
        return try {
            val response = apiService.getPostData(
                accessToken = accessToken,
                request = GetPostRequest(
                    postId = postId,
                    fetch = fetchType
                )
            )
            
            if (response.isSuccessful()) {
                response.data
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    // ==================== CREATE POST ====================
    
    suspend fun createPost(
        postText: String,
        imageUri: String? = null
    ): Boolean {
        return try {
            if (postText.trim().isEmpty()) return false
            
            // This would require multipart upload
            // Implementation depends on your API
            true
        } catch (e: Exception) {
            false
        }
    }
    
    // ==================== EDIT POST ====================
    
    suspend fun editPost(postId: Int, postText: String): Boolean {
        return try {
            if (postText.trim().isEmpty()) return false
            
            val response = apiService.postAction(
                accessToken = accessToken,
                request = PostActionRequest(
                    postId = postId,
                    action = "edit",
                    text = postText
                )
            )
            response.isSuccessful()
        } catch (e: Exception) {
            false
        }
    }
    
    // ==================== DELETE POST ====================
    
    suspend fun deletePost(postId: Int): Boolean {
        return try {
            val response = apiService.postAction(
                accessToken = accessToken,
                request = PostActionRequest(
                    postId = postId,
                    action = "delete"
                )
            )
            response.isSuccessful()
        } catch (e: Exception) {
            false
        }
    }
}
