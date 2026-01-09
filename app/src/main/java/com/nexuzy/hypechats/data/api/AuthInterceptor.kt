package com.nexuzy.hypechats.data.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import com.nexuzy.hypechats.util.HypechatsConfig

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        
        // Add headers to every request
        request = request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("User-Agent", "HypechatsAndroid/1.0")
            // Add Server Key header if your API requires it
            // .addHeader("X-Server-Key", HypechatsConfig.SERVER_KEY)
            .build()
        
        return chain.proceed(request)
    }
}
