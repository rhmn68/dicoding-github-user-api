package com.example.aplikasigithubuser.di

import com.example.aplikasigithubuser.di.remote.provideGithubServices
import com.example.aplikasigithubuser.di.remote.provideGson
import com.example.aplikasigithubuser.di.remote.provideOkHttpClient
import com.example.aplikasigithubuser.di.remote.provideRetrofit
import com.example.aplikasigithubuser.repository.searchuser.UsersRemoteSource
import com.example.aplikasigithubuser.repository.searchuser.UsersRepository

object Injection{
    fun provideMainRepository(): UsersRepository {
        val apiService = provideGithubServices(
            provideRetrofit(
                provideGson(),
                provideOkHttpClient()
            )
        )
        val remoteSource = UsersRemoteSource(apiService)
        return UsersRepository(remoteSource)
    }
}