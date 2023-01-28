package com.example.aplikasigithubuser.common.helper

import android.accounts.NetworkErrorException
import com.example.aplikasigithubuser.model.ErrorResponse
import com.example.aplikasigithubuser.model.ErrorResponse.Type.*
import com.example.aplikasigithubuser.model.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.internal.http.HTTP_INTERNAL_SERVER_ERROR
import okhttp3.internal.http.HTTP_NOT_FOUND
import okhttp3.internal.http.HTTP_UNAUTHORIZED
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.charset.Charset

object ApiHelper {

    suspend fun <T : Any> getResult(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call()
            parseOnSuccess(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(parseOnErrorResponseException(e))
        }
    }

    fun <T : Any> getFlowResult(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = call()
            emit(Resource.Loading(false))
            emit(parseOnSuccess(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Loading(false))
            emit(Resource.Error(parseOnErrorResponseException(e)))
        }
    }.flowOn(Dispatchers.IO)

    private fun <T : Any> parseOnSuccess(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            body?.let { responseBody ->
                parseBodyData(responseBody)
            } ?: Resource.Error(
                ErrorResponse(
                    message = response.message() ?: "Response body is empty",
                    type = NO_DATA,
                    code = response.code().toString()
                )
            )
        } else {
            val errorCode = response.code()
            val errorBodyString = errorBody(response)
            var errorResponse = ErrorResponse()

            if (!errorBodyString.isNullOrEmpty()) {
                try {
                    errorResponse = Gson().fromJson(errorBodyString, ErrorResponse::class.java)
                    errorResponse.code = errorCode.toString()
                } catch (e: Exception) {
                    errorResponse =
                        ErrorResponse(message = parseErrorMessage(errorCode), code = errorCode.toString())
                }
            }
            Resource.Error(errorResponse)
        }
    }

    private fun errorBody(response: Response<*>?): String? {
        val errorBody = response?.errorBody()
        val errorBodySource = errorBody?.source()
        errorBodySource?.request(Long.MAX_VALUE)

        return errorBodySource?.buffer?.clone()?.readString(Charset.forName("UTF-8"))
    }

    private fun <T> parseBodyData(responseBody: T): Resource<T> {
        return responseBody?.let { Resource.Success(it) } ?: Resource.Empty
    }

    private fun parseOnErrorResponseException(e: Exception): ErrorResponse = when (e) {
        is UnknownHostException -> ErrorResponse(
            message = "Bad Gateway",
            code = "502",
            type = UNRESOLVED_HOST
        )
        is SocketTimeoutException -> ErrorResponse(
            message = "Request Timeout",
            code = "408",
            type = REQUEST_TIMEOUT
        )
        is NetworkErrorException -> ErrorResponse(
            message = "No Internet Connection",
            type = NO_INTERNET_CONNECTION
        )
        is HttpException -> {
            when (e.code()) {
                302 -> ErrorResponse(message = "302", type = HOTSPOT_LOGIN)
                HTTP_UNAUTHORIZED -> ErrorResponse(message = "No Authorization", type = NO_AUTHORIZATION)
                HTTP_NOT_FOUND -> ErrorResponse(message = "Page Not Found", type = PAGE_NOT_FOUND)
                HTTP_INTERNAL_SERVER_ERROR -> ErrorResponse("Server Error", type = INTERNAL_SERVER_ERROR)
                else -> ErrorResponse(message = e.message ?: parseErrorMessage(e.code()))
            }
        }
        else -> ErrorResponse(message = "Internal Server Error", code = "500", type = GENERAL)
    }

    private fun parseErrorMessage(errorCode: Int): String = when (errorCode) {
        400 -> "Bad Request"
        401 -> "Unauthorized"
        403 -> "Forbidden"
        408 -> "Request Timeout"
        502 -> "Bad Gateway"
        504 -> "Gateway Timeout"
        else -> "Internal Server Error."
    }
}
