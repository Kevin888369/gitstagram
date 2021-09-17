package com.example.gitstagram.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gitstagram.main.MainViewModel
import com.example.gitstagram.network.GitApiStatus
import com.example.gitstagram.network.GitResponse
import com.example.gitstagram.network.GithubApi
import kotlinx.coroutines.launch

enum class FollowType(val string: String) {
    FOLLOWING("Following"),
    FOLLOWERS("Followers"),
}

class FollowViewModel : MainViewModel() {
    private val _followStatus = MutableLiveData<GitApiStatus>()
    private val _follow = MutableLiveData<List<GitResponse>>()
    private val _selectedName = MutableLiveData<String>()

    val followers: LiveData<List<GitResponse>> get() = _follow
    val followStatus: LiveData<GitApiStatus> get() = _followStatus
    private lateinit var type: FollowType

    private fun getUserFollowers() {
        viewModelScope.launch {
            _followStatus.value = GitApiStatus.LOADING
            try {
                _follow.value = when (type) {
                    FollowType.FOLLOWERS -> GithubApi.retrofitService.getUserFollowers(navigateToSelectedUser.value!!.loginName)
                    FollowType.FOLLOWING -> GithubApi.retrofitService.getUserFollowing(navigateToSelectedUser.value!!.loginName)
                }
                _followStatus.value = GitApiStatus.DONE
            } catch (e: Exception) {
                _follow.value = listOf()
                _followStatus.value = GitApiStatus.ERROR
            }
        }
    }

    fun setType(type: FollowType) {
        this.type = type
    }
}