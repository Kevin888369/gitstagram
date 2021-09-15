package com.example.gitstagram.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitUser (
    val id: Long,
    @Json(name = "followers_url") val followersUrl: String,
    @Json(name = "following_url") val followingUrl: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "login") val loginName: String,
    val type: String,
): Parcelable

@Parcelize
data class GitResponse(
    @Json(name = "total_count") val totalCount: Long,
    val items: List<GitUser>
): Parcelable