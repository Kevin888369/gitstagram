package com.example.gitstagram.apiClient

import com.example.gitstagram.network.GitResponse
import com.example.gitstagram.network.GitUserDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    suspend fun getSearchUsers(@Query("q") username: String): GitResponse

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): GitUserDetail
}