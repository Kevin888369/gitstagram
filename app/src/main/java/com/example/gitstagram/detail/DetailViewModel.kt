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
    private val _response = MutableLiveData<String>()
    private val _user = MutableLiveData<GitUserDetail>()
    private val _selectedUser = MutableLiveData<GitUser>()

    val selectedUser: LiveData<GitUser> get() = _selectedUser
    val status: LiveData<GitApiStatus> get() = _status
    val response: LiveData<String> get() = _response
    val user: LiveData<GitUserDetail> get() = _user

    init {
        _selectedUser.value = gitUser
        getGitUserDetail()
    }

    private fun getGitUserDetail() {
        viewModelScope.launch {
            _status.value = GitApiStatus.LOADING
            try {
                _user.value = GithubApi.retrofitService.getUserDetail(_selectedUser.value!!.loginName)
                _response.value = "Success: ${_user.value!!.name} user detail received"
                _status.value = GitApiStatus.DONE
                Log.d("response", _response.value!!)
            } catch (e: Exception) {
                _user.value = null
                _response.value = "Failed: ${e.message}"
                _status.value = GitApiStatus.ERROR
                Log.d("response", _response.value!!)
            }
        }
    }
}