package com.example.gitstagram.apiClient

import com.example.gitstagram.network.GitResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    suspend fun getAllUsers(@Query("q")username: String): GitResponse
}