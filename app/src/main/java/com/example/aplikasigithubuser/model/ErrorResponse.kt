package com.example.aplikasigithubuser.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null,
    var code: String = "500",
    var type: Type = Type.GENERAL
) {

    enum class Type {
        GENERAL,
        HOTSPOT_LOGIN,
        UNRESOLVED_HOST,
        NO_AUTHORIZATION,
        NO_INTERNET_CONNECTION,
        REQUEST_TIMEOUT,
        PAGE_NOT_FOUND,
        INTERNAL_SERVER_ERROR,
        NO_DATA,
    }
}
