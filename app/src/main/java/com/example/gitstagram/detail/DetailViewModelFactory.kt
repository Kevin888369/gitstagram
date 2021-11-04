package com.example.gitstagram.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitstagram.favorite.FavoriteViewModelFactory
import com.example.gitstagram.network.GitUser

class DetailViewModelFactory private constructor(private val mApplication: Application, private val mGitUser: GitUser) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: DetailViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application, gitUser: GitUser): DetailViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteViewModelFactory::class.java) {
                    INSTANCE = DetailViewModelFactory(application, gitUser)
                }
            }
            return INSTANCE as DetailViewModelFactory
        }
    }
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mGitUser, mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}