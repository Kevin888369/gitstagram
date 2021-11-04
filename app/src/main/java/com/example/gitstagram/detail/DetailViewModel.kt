package com.example.gitstagram.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gitstagram.Resource
import com.example.gitstagram.database.UserRepository
import com.example.gitstagram.network.GitUser
import com.example.gitstagram.network.GitUserDetail
import com.example.gitstagram.network.UserRetrofit

class DetailViewModel(gitUser: GitUser, application: Application) : ViewModel() {
    private val userRepo = UserRepository(application)
    private val _user = MutableLiveData<GitUserDetail>()
    private val _selectedUser = MutableLiveData(gitUser)

    fun insertUser(gitUser: GitUser) = userRepo.insert(gitUser)
    fun deleteUser(id: Long) = userRepo.delete(id)
    fun getUsers(id: Long) = userRepo.getUser(id)

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