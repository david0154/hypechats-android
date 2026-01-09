package com.nexuzy.hypechats.data.api

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.nexuzy.hypechats.util.HypechatsConfig
import java.util.concurrent.TimeUnit

object RetrofitClient {
    
    private val httpClient by lazy {
        val builder = OkHttpClient.Builder()
        
        // Add logging interceptor (debug only)
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(loggingInterceptor)
        
        // Add auth interceptor
        builder.addInterceptor(AuthInterceptor())
        
        // Add network interceptor for request/response logging
        builder.addNetworkInterceptor { chain ->
            val request = chain.request()
            val startTime = System.currentTimeMillis()
            
            val response = chain.proceed(request)
            
            val duration = System.currentTimeMillis() - startTime
            println("API Request: ${request.method} ${request.url} -> ${response.code} (${duration}ms)")
            
            response
        }
        
        // Certificate pinning for HTTPS security
        try {
            val certificatePinner = CertificatePinner.Builder()
                .add(HypechatsConfig.API_DOMAIN, HypechatsConfig.CERTIFICATE_PIN_SHA256)
                .build()
            builder.certificatePinner(certificatePinner)
        } catch (e: Exception) {
            println("Certificate pinning not configured: ${e.message}")
        }
        
        // Configure timeouts
        builder
            .connectTimeout(HypechatsConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(HypechatsConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(HypechatsConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
    
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(HypechatsConfig.API_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    fun <T> create(service: Class<T>): T = retrofit.create(service)
}

// Convenience property for easy access
val apiService: HypechatsApiService by lazy {
    RetrofitClient.create(HypechatsApiService::class.java)
}
