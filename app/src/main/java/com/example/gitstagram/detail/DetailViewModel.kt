package com.example.gitstagram.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.gitstagram.Resource
import com.example.gitstagram.network.*
import kotlinx.coroutines.launch

class DetailViewModel(@Suppress("UNUSED_PARAMETER") gitUser: GitUser) : ViewModel() {
    private val _user = MutableLiveData<GitUserDetail>()
    private val _selectedUser = MutableLiveData(gitUser)

    val selectedUser: LiveData<GitUser> get() = _selectedUser
    val user: LiveData<GitUserDetail> get() = _user

    val followers = Transformations.map(user) {
        it.followers.toString()
    }
    val following = Transformations.map(user) {
        it.following.toString()
    }
    val repo = Transformations.map(user) {
        it.repo.toString()
    }

    fun setUser(user: GitUserDetail) {
        _user.value = user
    }

    val detailResult: LiveData<Resource<GitUserDetail>> = Transformations
        .switchMap(_selectedUser){
            UserRetrofit.getDetailUser(it.loginName)
        }
}