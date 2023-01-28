package com.example.aplikasigithubuser.repository.searchuser

import com.example.aplikasigithubuser.common.helper.ApiHelper
import com.example.aplikasigithubuser.di.remote.GithubServices

class UsersRemoteSource constructor(
    private val services: GithubServices
) {
    suspend fun getPagingUsers(
        keyword: String,
        page: Int,
        perPage: Int
    ) = ApiHelper.getResult {
        services.getUsers(keyword, page, perPage)
    }

    suspend fun getDetailUserResult(username: String) = ApiHelper.getResult { services.getDetailUser(username) }
}