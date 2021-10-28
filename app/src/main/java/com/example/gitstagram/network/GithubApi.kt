package com.example.gitstagram.network

import com.example.gitstagram.BuildConfig
import com.example.gitstagram.apiClient.GithubApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.github.com"

private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

val clientInterceptor = Interceptor { chain ->
    var request: Request = chain.request()
    val requestBuilder =
        request.newBuilder().addHeader("Authorization", BuildConfig.TOKEN)
    request = requestBuilder.build()
    chain.proceed(request)
}

private val client = OkHttpClient.Builder()
    .addNetworkInterceptor(clientInterceptor)
    .addInterceptor(loggingInterceptor)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()


object GithubApi {
    val retrofitService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }
}