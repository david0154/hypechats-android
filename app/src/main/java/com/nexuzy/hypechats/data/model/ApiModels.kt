package com.nexuzy.hypechats.data.model

import com.google.gson.annotations.SerializedName

// ==================== API RESPONSE ====================

data class ApiResponse<T>(
    @SerializedName("status_code")
    val statusCode: Int = 200,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("error")
    val errors: ApiError? = null
) {
    fun isSuccessful(): Boolean = statusCode in 200..299
}

data class ApiError(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("error_text")
    val errorText: String = ""
)

// ==================== AUTHENTICATION ====================

data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)

data class LoginResponse(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String = "Bearer"
)

data class SignupRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirm_password")
    val confirmPassword: String
)

data class SocialLoginRequest(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("google_key")
    val googleKey: String? = null
)

// ==================== USER ====================

data class User(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("last_name")
    val lastName: String = "",
    @SerializedName("bio")
    val bio: String = "",
    @SerializedName("profile_picture")
    val profilePicture: String = "",
    @SerializedName("cover_picture")
    val coverPicture: String = "",
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("website")
    val website: String = "",
    @SerializedName("followers_count")
    val followersCount: Int = 0,
    @SerializedName("following_count")
    val followingCount: Int = 0,
    @SerializedName("posts_count")
    val postsCount: Int = 0,
    @SerializedName("is_verified")
    val isVerified: Boolean = false,
    @SerializedName("is_following")
    val isFollowing: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String = ""
)

data class GetUserRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("fetch")
    val fetch: String = "user_data"
)

data class UserDataResponse(
    @SerializedName("user_data")
    val userData: User? = null,
    @SerializedName("followers")
    val followers: List<User>? = null,
    @SerializedName("following")
    val following: List<User>? = null
)

data class UpdateUserRequest(
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    @SerializedName("bio")
    val bio: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("website")
    val website: String? = null
)

data class FollowRequest(
    @SerializedName("user_id")
    val userId: Int
)

data class SuggestionsResponse(
    @SerializedName("suggestions")
    val suggestions: List<User> = emptyList()
)

// ==================== POSTS ====================

data class Post(
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("user_profile_picture")
    val userProfilePicture: String = "",
    @SerializedName("post_text")
    val postText: String,
    @SerializedName("post_image")
    val postImage: String? = null,
    @SerializedName("likes_count")
    val likesCount: Int = 0,
    @SerializedName("comments_count")
    val commentsCount: Int = 0,
    @SerializedName("is_liked")
    val isLiked: Boolean = false,
    @SerializedName("is_disliked")
    val isDisliked: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)

data class GetPostRequest(
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("fetch")
    val fetch: String = "post_data"
)

data class PostDataResponse(
    @SerializedName("post_data")
    val postData: Post? = null,
    @SerializedName("post_comments")
    val postComments: List<Comment>? = null,
    @SerializedName("post_liked_users")
    val postLikedUsers: List<User>? = null
)

data class PostActionRequest(
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("action")
    val action: String,
    @SerializedName("text")
    val text: String? = null
)

data class CreatePostRequest(
    @SerializedName("post_text")
    val postText: String,
    @SerializedName("post_image")
    val postImage: String? = null
)

// ==================== COMMENTS ====================

data class Comment(
    @SerializedName("comment_id")
    val commentId: Int,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("user_profile_picture")
    val userProfilePicture: String = "",
    @SerializedName("comment_text")
    val commentText: String,
    @SerializedName("likes_count")
    val likesCount: Int = 0,
    @SerializedName("is_liked")
    val isLiked: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String = ""
)

// ==================== SEARCH ====================

data class SearchRequest(
    @SerializedName("search_key")
    val searchKey: String
)

data class SearchResponse(
    @SerializedName("users")
    val users: List<User>? = null,
    @SerializedName("posts")
    val posts: List<Post>? = null
)
