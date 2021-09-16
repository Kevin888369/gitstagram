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