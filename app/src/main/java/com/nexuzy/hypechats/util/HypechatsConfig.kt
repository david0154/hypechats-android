package com.nexuzy.hypechats.util

object HypechatsConfig {
    // ==================== API CONFIGURATION ====================
    const val API_BASE_URL = "https://your-api.hypechats.com/api/"
    const val API_DOMAIN = "your-api.hypechats.com"
    const val SERVER_KEY = "your-server-key-here"
    
    // Certificate SHA-256 pin for HTTPS
    // Get your cert pin: openssl s_client -connect domain.com:443 -showcerts
    const val CERTIFICATE_PIN_SHA256 = "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="
    
    // ==================== TIMEOUTS (in seconds) ====================
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val TIMEOUT_SECONDS = 30L
    
    // ==================== STORAGE KEYS ====================
    const val PREF_ACCESS_TOKEN = "pref_access_token"
    const val PREF_USER_ID = "pref_user_id"
    const val PREF_USERNAME = "pref_username"
    const val PREF_USER_EMAIL = "pref_user_email"
    const val PREF_USER_PROFILE_PICTURE = "pref_user_profile_picture"
    const val PREF_USER_BIO = "pref_user_bio"
    
    // ==================== APP CONFIG ====================
    const val APP_VERSION = "1.0.0"
    const val MIN_PASSWORD_LENGTH = 6
    const val MIN_USERNAME_LENGTH = 3
    const val FEED_PAGE_SIZE = 20
    
    // ==================== SOCIAL LOGIN ====================
    const val GOOGLE_PROVIDER = "google"
    const val FACEBOOK_PROVIDER = "facebook"
    const val TWITTER_PROVIDER = "twitter"
    const val LINKEDIN_PROVIDER = "linkedin"
    
    // ==================== API ENDPOINTS ====================
    object Endpoints {
        // Auth
        const val LOGIN = "auth/login"
        const val SIGNUP = "auth/signup"
        const val SOCIAL_LOGIN = "auth/social-login"
        const val REFRESH_TOKEN = "auth/refresh-token"
        const val LOGOUT = "auth/logout"
        
        // Users
        const val GET_USER = "users/{id}"
        const val UPDATE_USER = "users/update"
        const val GET_FOLLOWERS = "users/{id}/followers"
        const val GET_FOLLOWING = "users/{id}/following"
        const val FOLLOW_USER = "users/{id}/follow"
        const val UNFOLLOW_USER = "users/{id}/unfollow"
        const val GET_SUGGESTIONS = "users/suggestions"
        const val SEARCH_USERS = "users/search"
        
        // Posts
        const val GET_FEED = "posts/feed"
        const val GET_POST = "posts/{id}"
        const val CREATE_POST = "posts/create"
        const val UPDATE_POST = "posts/{id}/update"
        const val DELETE_POST = "posts/{id}/delete"
        const val LIKE_POST = "posts/{id}/like"
        const val UNLIKE_POST = "posts/{id}/unlike"
        const val GET_POST_LIKES = "posts/{id}/likes"
        
        // Comments
        const val GET_COMMENTS = "posts/{id}/comments"
        const val CREATE_COMMENT = "posts/{id}/comments"
        const val UPDATE_COMMENT = "comments/{id}/update"
        const val DELETE_COMMENT = "comments/{id}/delete"
        const val LIKE_COMMENT = "comments/{id}/like"
        const val UNLIKE_COMMENT = "comments/{id}/unlike"
    }
}
