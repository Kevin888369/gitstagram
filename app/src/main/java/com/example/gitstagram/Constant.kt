package com.example.gitstagram

const val DATABASE_NAME = "Gitstagram.db"
const val TABLE_NAME = "favouriteUsers"

const val DB_AUTHORITY = "com.example.gitstagram"
const val SCHEME = "content"
const val DB_CONTENT_URI = "$SCHEME://$DB_AUTHORITY"

const val USER_TABLE_NAME = "favouriteUsers"
const val USER_CONTENT_URI = "$DB_CONTENT_URI/$USER_TABLE_NAME"