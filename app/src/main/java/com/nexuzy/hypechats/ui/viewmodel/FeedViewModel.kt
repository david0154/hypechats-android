package com.nexuzy.hypechats.ui.viewmodel

import androidx.lifecycle.*
import com.nexuzy.hypechats.data.repository.FeedRepository
import com.nexuzy.hypechats.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
    
    // ==================== UI STATE ====================
    
    private val _posts = MutableLiveData<List<Post>>(emptyList())
    val posts: LiveData<List<Post>> = _posts
    
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error
    
    private val _refreshing = MutableLiveData(false)
    val refreshing: LiveData<Boolean> = _refreshing
    
    private var currentOffset = 0
    private val pageSize = 20
    
    init {
        loadFeed()
    }
    
    // ==================== LOAD FEED ====================
    
    fun loadFeed() = viewModelScope.launch {
        try {
            _isLoading.value = true
            _error.value = null
            currentOffset = 0
            
            val result = feedRepository.getNewsFeed(offset = 0)
            _posts.value = result
        } catch (e: Exception) {
            _error.value = e.message ?: "Failed to load feed"
        } finally {
            _isLoading.value = false
        }
    }
    
    fun loadMorePosts() = viewModelScope.launch {
        try {
            val result = feedRepository.getNewsFeed(offset = currentOffset + pageSize)
            if (result.isNotEmpty()) {
                currentOffset += pageSize
                val currentPosts = _posts.value ?: emptyList()
                _posts.value = currentPosts + result
            }
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
    
    // ==================== REFRESH FEED ====================
    
    fun refreshFeed() = viewModelScope.launch {
        try {
            _refreshing.value = true
            currentOffset = 0
            
            val result = feedRepository.getNewsFeed(offset = 0)
            _posts.value = result
        } catch (e: Exception) {
            _error.value = e.message
        } finally {
            _refreshing.value = false
        }
    }
    
    // ==================== LIKE POST ====================
    
    fun likePost(postId: Int) = viewModelScope.launch {
        try {
            val result = feedRepository.likePost(postId)
            if (result) {
                updatePostLikeStatus(postId, true)
            }
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
    
    fun unlikePost(postId: Int) = viewModelScope.launch {
        try {
            val result = feedRepository.unlikePost(postId)
            if (result) {
                updatePostLikeStatus(postId, false)
            }
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
    
    // ==================== DISLIKE POST ====================
    
    fun dislikePost(postId: Int) = viewModelScope.launch {
        try {
            val result = feedRepository.dislikePost(postId)
            if (result) {
                updatePostDislikeStatus(postId, true)
            }
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
    
    fun undislikePost(postId: Int) = viewModelScope.launch {
        try {
            val result = feedRepository.undislikePost(postId)
            if (result) {
                updatePostDislikeStatus(postId, false)
            }
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
    
    // ==================== UTILITY ====================
    
    private fun updatePostLikeStatus(postId: Int, liked: Boolean) {
        val currentPosts = _posts.value ?: return
        _posts.value = currentPosts.map { post ->
            if (post.postId == postId) {
                post.copy(
                    isLiked = liked,
                    likesCount = if (liked) post.likesCount + 1 else maxOf(0, post.likesCount - 1)
                )
            } else post
        }
    }
    
    private fun updatePostDislikeStatus(postId: Int, disliked: Boolean) {
        val currentPosts = _posts.value ?: return
        _posts.value = currentPosts.map { post ->
            if (post.postId == postId) {
                post.copy(
                    isDisliked = disliked
                )
            } else post
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}
