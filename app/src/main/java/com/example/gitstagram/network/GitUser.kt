package com.example.gitstagram.network

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitstagram.TABLE_NAME
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class GitUser (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "url")
    val url: String,
    @Json(name = "avatar_url")
    @ColumnInfo(name = "image_url")
    val avatarUrl: String,
    @Json(name = "login")
    @ColumnInfo(name = "name")
    val loginName: String,
    @ColumnInfo(name = "type")
    val type: String,
): Parcelable

