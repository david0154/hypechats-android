package com.nexuzy.hypechats.data.api

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.nexuzy.hypechats.util.HypechatsConfig
import com.nexuzy.hypechats.util.WebToAppBridge
import com.nexuzy.hypechats.data.local.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Activity to load and display web content with native app bridge
 * Useful for hybrid apps that load web content
 */
@AndroidEntryPoint
class WebViewActivity : AppCompatActivity() {
    
    @Inject
    lateinit var tokenManager: TokenManager
    
    private lateinit var webView: WebView
    private lateinit var bridge: WebToAppBridge
    
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        webView = WebView(this)
        setContentView(webView)
        
        // Configure WebView
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            userAgentString = getUserAgent()
        }
        
        // Set up web view client
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: android.graphics.Bitmap?) {
                println("WebView loading: $url")
            }
            
            override fun onPageFinished(view: WebView, url: String) {
                println("WebView loaded: $url")
                // Inject token after page loads
                injectAuthToken()
            }
        }
        
        // Create bridge
        bridge = WebToAppBridge(this, tokenManager, webView)
        
        // Load web URL
        val webUrl = intent.getStringExtra("WEB_URL") ?: HypechatsConfig.API_BASE_URL
        webView.loadUrl(webUrl)
    }
    
    private fun injectAuthToken() {
        val token = tokenManager.getToken()
        if (token.isNotEmpty()) {
            val js = """
                window.authToken = '$token';
                document.querySelectorAll('*').forEach(el => {
                    if(el.dataset) el.dataset.authToken = '$token';
                });
            """.trimIndent()
            webView.evaluateJavascript(js, null)
        }
    }
    
    private fun getUserAgent(): String {
        val appVersion = HypechatsConfig.APP_VERSION
        val androidVersion = android.os.Build.VERSION.RELEASE
        return "HypechatsApp/$appVersion Android/$androidVersion"
    }
    
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
