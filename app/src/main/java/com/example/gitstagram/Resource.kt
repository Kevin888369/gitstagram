package com.example.gitstagram

import com.example.gitstagram.network.GitApiStatus

class Resource<out E>(val status: GitApiStatus, val data: E?, val message: String?) {
    companion object {
        fun <E> done(data: E): Resource<E> = Resource(GitApiStatus.DONE, data, null)

        fun <E> error(data: E?, message: String): Resource<E> = Resource(GitApiStatus.ERROR, data, message)

        fun <E> loading(data: E?): Resource<E> = Resource(GitApiStatus.LOADING, data, null)
    }
}

