package com.example.aplikasigithubuser.model


sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorResponse: ErrorResponse) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
    object Empty : Resource<Nothing>()
}
