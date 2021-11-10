package com.example.gitstagram.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitUser (
    val id: Long,
    val url: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "login") val loginName: String,
    val type: String,
): Parcelable

@Parcelize
data class GitUserDetail (
    val id: Long,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "login") val loginName: String,
    @Json(name = "public_repos") val repo: Long,
    val followers: Long,
    val following: Long,
    val type: String,
    val location: String?,
    val company: String?
): Parcelable

@Parcelize
data class GitResponse(
    @Json(name = "total_count") val totalCount: Long,
    val items: List<GitUser>
): Parcelable