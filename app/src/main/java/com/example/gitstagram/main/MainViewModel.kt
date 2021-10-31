package com.example.gitstagram.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gitstagram.Resource
import com.example.gitstagram.network.GitUser
import com.example.gitstagram.network.UserRetrofit

open class MainViewModel: ViewModel() {
    private val _searchText = MutableLiveData<String>()
    private val _users = MutableLiveData<List<GitUser>>()
    val users: LiveData<List<GitUser>> get() = _users

    val searchResult: LiveData<Resource<List<GitUser>>> = Transformations
        .switchMap(_searchText){name ->
            UserRetrofit.searchUsers(name)
        }

    private val _navigateToSelectedUser = MutableLiveData<GitUser?>()
    val navigateToSelectedUser: LiveData<GitUser?> get() = _navigateToSelectedUser


    fun setSearchText(text: String) {
        _searchText.value = text
    }

    fun setUsers(users: List<GitUser>) {
        _users.value = users
    }


    fun displayUserDetail(gitUser: GitUser) {
        _navigateToSelectedUser.value = gitUser
    }

    fun doneDisplayUserDetail() {
        _navigateToSelectedUser.value = null
    }
}