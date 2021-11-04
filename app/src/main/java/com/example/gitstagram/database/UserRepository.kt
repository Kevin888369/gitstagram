package com.example.gitstagram.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.gitstagram.network.GitUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application){
    private val daoUser: DaoUser
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        daoUser = db.userDao()
    }
    fun getAllUsers(): LiveData<List<GitUser>> = daoUser.getAllUsers()
    fun getUser(id: Long): LiveData<GitUser> = daoUser.getUser(id)
    fun insert(gitUser: GitUser) {
        executorService.execute { daoUser.insertUser(gitUser) }
    }
    fun delete(id: Long) {
        executorService.execute { daoUser.deleteUser(id) }
    }
}