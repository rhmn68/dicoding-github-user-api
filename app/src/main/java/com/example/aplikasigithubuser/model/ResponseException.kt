package com.example.aplikasigithubuser.model

sealed class ResponseException : Exception() {
    data class Error(val errorResponse: ErrorResponse) : ResponseException()
    object Empty : ResponseException()
}