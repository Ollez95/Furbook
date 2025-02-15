package com.example.core.utils

sealed class Response<out T> {
    data object Loading : Response<Nothing>()
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Response<Nothing>()
}