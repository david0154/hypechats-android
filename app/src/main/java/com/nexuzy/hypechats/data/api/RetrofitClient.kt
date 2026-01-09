package com.nexuzy.hypechats.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.nexuzy.hypechats.util.HypechatsConfig
import java.util.concurrent.TimeUnit

object RetrofitClient {
    
    private val httpClient by lazy {
        val builder = OkHttpClient.Builder()
        
        // Add logging interceptor (only in debug)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        
        // Add auth interceptor
        builder.addInterceptor(AuthInterceptor())
        
        // Configure timeouts
        builder
            .connectTimeout(HypechatsConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(HypechatsConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(HypechatsConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
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

// Convenience property
val apiService: HypechatsApiService by lazy {
    RetrofitClient.create(HypechatsApiService::class.java)
}
