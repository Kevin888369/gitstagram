package com.example.gitstagram.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

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