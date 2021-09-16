package com.example.gitstagram.apiClient

import com.example.gitstagram.network.GitResponse
import com.example.gitstagram.network.GitUserDetail
import retrofit2.http.*

interface GithubApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    suspend fun getSearchUsers(@Query("q") username: String): GitResponse

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    suspend fun getUserDetail(@Path("username") username: String): GitUserDetail
}