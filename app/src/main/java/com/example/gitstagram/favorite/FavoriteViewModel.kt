package com.example.gitstagram.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitstagram.database.UserRepository
import com.example.gitstagram.network.GitUser

class FavoriteViewModel(application: Application) : ViewModel() {
    private val repo = UserRepository(application)
    private val _favoriteUsers = MutableLiveData<List<GitUser>>()
    val favoriteUsers: LiveData<List<GitUser>> get() = _favoriteUsers

    private val _navigateToSelectedUser = MutableLiveData<GitUser?>()
    val navigateToSelectedUser: LiveData<GitUser?> get() = _navigateToSelectedUser

    @JvmName("getAllFavoriteUsers")
    fun getFavoriteUsers(): LiveData<List<GitUser>> = repo.getAllUsers()

    fun setFavoriteUsers(list: List<GitUser>) {
        _favoriteUsers.value = list
    }

    fun displayUserDetail(gitUser: GitUser) {
        _navigateToSelectedUser.value = gitUser
    }

    fun doneDisplayUserDetail() {
        _navigateToSelectedUser.value = null
    }
}