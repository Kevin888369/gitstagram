package com.example.gitstagram.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.gitstagram.Resource
import com.example.gitstagram.main.MainViewModel
import com.example.gitstagram.network.*
import kotlinx.coroutines.launch

enum class FollowType(val string: String) {
    FOLLOWING("Following"),
    FOLLOWERS("Followers");

    companion object {
        fun from(findValue: String): FollowType = values().first { it.string == findValue }
    }
}

class FollowViewModel : MainViewModel() {
    private val _follow = MutableLiveData<List<GitUser>>()
    private val _type = MutableLiveData<FollowType>()
    private val _selectedName = MutableLiveData<String>()
    val followResult: LiveData<Resource<List<GitUser>>> = Transformations
    .switchMap(_selectedName) {name ->
        when(_type.value) {
            FollowType.FOLLOWERS -> {
                UserRetrofit.getFollowers(name)
            }
            FollowType.FOLLOWING -> {
                UserRetrofit.getFollowing(name)
            }
            else -> null
        }
    }

    val follow: LiveData<List<GitUser>> get() = _follow
    val type: LiveData<FollowType> get() = _type


    fun setFollow(type: FollowType, username: String) {
        _type.value = type
        _selectedName.value = username
    }

    fun setData(follow: List<GitUser>) {
        _follow.value = follow
    }

}