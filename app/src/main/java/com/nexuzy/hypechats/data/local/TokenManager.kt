package com.nexuzy.hypechats.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.nexuzy.hypechats.util.HypechatsConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(context: Context) {
    
    private val encryptedSharedPreferences: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        
        EncryptedSharedPreferences.create(
            context,
            "hypechats_secure",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    
    // Save token
    fun saveToken(token: String) {
        encryptedSharedPreferences.edit()
            .putString(HypechatsConfig.PREF_ACCESS_TOKEN, token)
            .apply()
    }
    
    // Get token
    fun getToken(): String {
        return encryptedSharedPreferences.getString(HypechatsConfig.PREF_ACCESS_TOKEN, "") ?: ""
    }
    
    // Save user ID
    fun saveUserId(userId: Int) {
        encryptedSharedPreferences.edit()
            .putInt(HypechatsConfig.PREF_USER_ID, userId)
            .apply()
    }
    
    // Get user ID
    fun getUserId(): Int {
        return encryptedSharedPreferences.getInt(HypechatsConfig.PREF_USER_ID, 0)
    }
    
    // Save username
    fun saveUsername(username: String) {
        encryptedSharedPreferences.edit()
            .putString(HypechatsConfig.PREF_USERNAME, username)
            .apply()
    }
    
    // Get username
    fun getUsername(): String {
        return encryptedSharedPreferences.getString(HypechatsConfig.PREF_USERNAME, "") ?: ""
    }
    
    // Save email
    fun saveEmail(email: String) {
        encryptedSharedPreferences.edit()
            .putString(HypechatsConfig.PREF_USER_EMAIL, email)
            .apply()
    }
    
    // Get email
    fun getEmail(): String {
        return encryptedSharedPreferences.getString(HypechatsConfig.PREF_USER_EMAIL, "") ?: ""
    }
    
    // Clear all tokens
    fun clearTokens() {
        encryptedSharedPreferences.edit()
            .clear()
            .apply()
    }
    
    // Check if user is logged in
    fun isLoggedIn(): Boolean {
        return getToken().isNotEmpty() && getUserId() > 0
    }
}
