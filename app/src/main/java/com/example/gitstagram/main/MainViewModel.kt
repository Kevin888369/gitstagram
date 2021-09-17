package com.example.gitstagram.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitstagram.network.GitApiStatus
import com.example.gitstagram.network.GitUser
import com.example.gitstagram.network.GithubApi
import kotlinx.coroutines.launch

open class MainViewModel: ViewModel() {
    private val _status = MutableLiveData<GitApiStatus>()
    private val _users = MutableLiveData<List<GitUser>>()
    private val _searchText = MutableLiveData<String>()
    private val _navigateToSelectedUser = MutableLiveData<GitUser?>()

    open val status: LiveData<GitApiStatus> get() = _status
    val users: LiveData<List<GitUser>> get() = _users
    val searchText: LiveData<String> get() = _searchText
    val navigateToSelectedUser: LiveData<GitUser?> get() = _navigateToSelectedUser

    init {
        getGitUsers()
    }

    private fun getGitUsers() {
        viewModelScope.launch {
            _status.value = GitApiStatus.LOADING
            try {
                if (!_searchText.value.isNullOrBlank()) {
                    val response = GithubApi.retrofitService.getSearchUsers(_searchText.value!!)
                    _users.value = response.items
                    _status.value = GitApiStatus.DONE

                }
            } catch (e: Exception) {
                _users.value = listOf()
                _status.value = GitApiStatus.ERROR
            }
        }
    }

    fun setSearchText(text: String) {
        _searchText.value = text
    }

    fun updateUsers() {
        getGitUsers()
    }

    fun displayUserDetail(gitUser: GitUser) {
        _navigateToSelectedUser.value = gitUser
    }

    fun doneDisplayUserDetail() {
        _navigateToSelectedUser.value = null
    }
}