package com.example.gitstagram.apiClient

import com.example.gitstagram.network.GitResponse
import com.example.gitstagram.network.GitUserDetail
import retrofit2.http.*

interface GithubApiService {
    @GET("search/users")
    suspend fun getSearchUsers(@Query("q") username: String): GitResponse

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): GitUserDetail

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): List<GitResponse>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): List<GitResponse>
}