package com.example.gitstagram.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gitstagram.TABLE_NAME
import com.example.gitstagram.network.GitUser

@Dao
interface DaoUser {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllUsers(): LiveData<List<GitUser>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :userId")
    fun getUser(userId: Long): LiveData<GitUser>

    @Query("SELECT EXISTS(SELECT * FROM $TABLE_NAME WHERE id = :userId)")
    fun isUserFavorited(userId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userEntity: GitUser)

    @Query("DELETE FROM $TABLE_NAME WHERE id = :userId")
    fun deleteUser(userId: Long)

}