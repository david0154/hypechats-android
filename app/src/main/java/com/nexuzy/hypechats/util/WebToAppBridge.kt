package com.nexuzy.hypechats.util

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.nexuzy.hypechats.data.local.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Bridge between Web and Native Android App
 * Allows secure communication between WebView and Android native code
 * 
 * Usage in JavaScript:
 * ```
 * // Call native Android methods from JavaScript
 * window.NativeAndroid.saveAuthToken(token, userId, username)
 * const token = window.NativeAndroid.getAuthToken()
 * window.NativeAndroid.showToast("Message")
 * ```
 */
class WebToAppBridge(
    private val context: Context,
    private val tokenManager: TokenManager,
    private val webView: WebView
) {
    
    private val gson = Gson()
    
    init {
        // Register JavaScript interface
        webView.addJavascriptInterface(this, "NativeAndroid")
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
    }
    
    // ==================== AUTHENTICATION ====================
    
    @JavascriptInterface
    fun saveAuthToken(accessToken: String, userId: Int, username: String) {
        CoroutineScope(Dispatchers.Main).launch {
            tokenManager.saveToken(accessToken)
            tokenManager.saveUserId(userId)
            tokenManager.saveUsername(username)
            Toast.makeText(context, "Authenticated successfully", Toast.LENGTH_SHORT).show()
            println("Auth token saved for user: $username")
        }
    }
    
    @JavascriptInterface
    fun getAuthToken(): String {
        return tokenManager.getToken()
    }
    
    @JavascriptInterface
    fun getUsername(): String {
        return tokenManager.getUsername()
    }
    
    @JavascriptInterface
    fun getUserId(): Int {
        return tokenManager.getUserId()
    }
    
    @JavascriptInterface
    fun clearAuthToken() {
        CoroutineScope(Dispatchers.Main).launch {
            tokenManager.clearTokens()
            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            println("Auth token cleared")
        }
    }
    
    @JavascriptInterface
    fun isLoggedIn(): Boolean {
        return tokenManager.isLoggedIn()
    }
    
    // ==================== NOTIFICATIONS ====================
    
    @JavascriptInterface
    fun showToast(message: String, duration: String = "SHORT") {
        val length = if (duration == "LONG") Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(context, message, length).show()
    }
    
    @JavascriptInterface
    fun showNotification(title: String, message: String) {
        println("Notification - $title: $message")
        // Can be extended to show system notifications
    }
    
    // ==================== DATA TRANSFER ====================
    
    @JavascriptInterface
    fun sendDataToNative(jsonData: String): Boolean {
        return try {
            val data = gson.fromJson(jsonData, Map::class.java)
            println("Received from web: $data")
            true
        } catch (e: Exception) {
            println("Error parsing JSON: ${e.message}")
            false
        }
    }
    
    @JavascriptInterface
    fun getDataFromNative(key: String): String {
        return when (key) {
            "username" -> tokenManager.getUsername()
            "email" -> tokenManager.getEmail()
            "user_id" -> tokenManager.getUserId().toString()
            "access_token" -> tokenManager.getToken()
            "is_logged_in" -> tokenManager.isLoggedIn().toString()
            else -> ""
        }
    }
    
    // ==================== NAVIGATION ====================
    
    @JavascriptInterface
    fun navigateTo(destination: String, params: String = "") {
        println("Navigate to: $destination with params: $params")
        // Can be extended to trigger native navigation
    }
    
    @JavascriptInterface
    fun goBack() {
        if (webView.canGoBack()) {
            webView.goBack()
        }
    }
    
    @JavascriptInterface
    fun goForward() {
        if (webView.canGoForward()) {
            webView.goForward()
        }
    }
    
    // ==================== DEVICE INFO ====================
    
    @JavascriptInterface
    fun getDeviceInfo(): String {
        val info = mapOf(
            "device" to android.os.Build.DEVICE,
            "model" to android.os.Build.MODEL,
            "manufacturer" to android.os.Build.MANUFACTURER,
            "android_version" to android.os.Build.VERSION.RELEASE,
            "sdk_version" to android.os.Build.VERSION.SDK_INT,
            "app_version" to HypechatsConfig.APP_VERSION
        )
        return gson.toJson(info)
    }
    
    @JavascriptInterface
    fun getApiBaseUrl(): String {
        return HypechatsConfig.API_BASE_URL
    }
    
    // ==================== LOGGING ====================
    
    @JavascriptInterface
    fun logMessage(tag: String, message: String) {
        println("[WebView-$tag] $message")
    }
}
