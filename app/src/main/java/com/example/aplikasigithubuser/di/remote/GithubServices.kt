package com.example.aplikasigithubuser.di.remote

import com.example.aplikasigithubuser.model.response.SearchUsersResponse
import com.example.aplikasigithubuser.model.response.UserDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubServices {
    @GET("search/users")
    fun getUsers(
        @Query("q") keyword: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
    ): Response<SearchUsersResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Response<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String
    ): Response<SearchUsersResponse>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Response<SearchUsersResponse>
}