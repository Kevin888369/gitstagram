package com.example.gitstagram.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.gitstagram.network.GitApiStatus
import com.example.gitstagram.network.GitUser
import com.example.gitstagram.network.GitUserDetail
import com.example.gitstagram.network.GithubApi
import kotlinx.coroutines.launch

class DetailViewModel(@Suppress("UNUSED_PARAMETER") gitUser: GitUser) : ViewModel() {
    private val _status = MutableLiveData<GitApiStatus>()
    private val _user = MutableLiveData<GitUserDetail>()
    private val _selectedUser = MutableLiveData<GitUser>()

    val selectedUser: LiveData<GitUser> get() = _selectedUser
    val status: LiveData<GitApiStatus> get() = _status
    val user: LiveData<GitUserDetail> get() = _user

    val followers = Transformations.map(user) {
        it.followers.toString()
    }!!
    val following = Transformations.map(user) {
        it.following.toString()
    }!!
    val repo = Transformations.map(user) {
        it.repo.toString()
    }!!

    init {
        _selectedUser.value = gitUser
        getGitUserDetail()
    }

    private fun getGitUserDetail() {
        viewModelScope.launch {
            _status.value = GitApiStatus.LOADING
            try {
                _user.value = GithubApi.retrofitService.getUserDetail(_selectedUser.value!!.loginName)
                _status.value = GitApiStatus.DONE
            } catch (e: Exception) {
                _user.value = null
                _status.value = GitApiStatus.ERROR
            }
        }
    }
}