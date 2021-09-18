package com.example.gitstagram.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gitstagram.main.MainViewModel
import com.example.gitstagram.network.GitApiStatus
import com.example.gitstagram.network.GitResponse
import com.example.gitstagram.network.GitUser
import com.example.gitstagram.network.GithubApi
import kotlinx.coroutines.launch

enum class FollowType(val string: String) {
    FOLLOWING("Following"),
    FOLLOWERS("Followers");

    companion object {
        fun from(findValue: String): FollowType = values().first { it.string == findValue }
    }
}

class FollowViewModel : MainViewModel() {
    private val _followStatus = MutableLiveData<GitApiStatus>()
    private val _follow = MutableLiveData<List<GitUser>>()
    private val _type = MutableLiveData<FollowType>()
    private val _selectedName = MutableLiveData<String>()

    val follow: LiveData<List<GitUser>> get() = _follow
    val followStatus: LiveData<GitApiStatus> get() = _followStatus
    val type: LiveData<FollowType> get() = _type

    init {
        getUserFollowers()
    }

    private fun getUserFollowers() {
        viewModelScope.launch {
            _followStatus.value = GitApiStatus.LOADING
            try {
                _follow.value = when (type.value) {
                    FollowType.FOLLOWERS -> GithubApi.retrofitService.getUserFollowers(_selectedName.value!!)
                    FollowType.FOLLOWING -> GithubApi.retrofitService.getUserFollowing(_selectedName.value!!)
                    else -> null
                }
                _followStatus.value = GitApiStatus.DONE
            } catch (e: Exception) {
                _follow.value = listOf()
                _followStatus.value = GitApiStatus.ERROR
            }
        }
    }

    fun setFollow(type: FollowType, username: String) {
        _type.value = type
        _selectedName.value = username
    }

    fun updateData() {
        getUserFollowers()
    }

}