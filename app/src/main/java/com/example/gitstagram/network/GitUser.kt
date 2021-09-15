package com.example.gitstagram.network

import com.squareup.moshi.Json

data class GitUser (
    val id: Long,
    @Json(name = "followers_url") val followersUrl: String,
    @Json(name = "following_url") val followingUrl: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    val name: String,
    val company: String,
    val location: String,
    val followers: Int,
    val following: Int,
)