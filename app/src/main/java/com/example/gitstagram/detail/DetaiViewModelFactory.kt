package com.example.gitstagram.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitstagram.network.GitUser

class DetailViewModelFactory(
    private val gitUser: GitUser
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(gitUser) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}