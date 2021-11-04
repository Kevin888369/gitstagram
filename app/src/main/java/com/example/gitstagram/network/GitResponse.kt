package com.example.gitstagram.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitResponse(
    @Json(name = "total_count") val totalCount: Long,
    val items: List<GitUser>
): Parcelable