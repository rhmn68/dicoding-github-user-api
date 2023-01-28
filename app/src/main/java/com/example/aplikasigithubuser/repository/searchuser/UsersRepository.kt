package com.example.aplikasigithubuser.repository.searchuser

import androidx.paging.Pager
import androidx.paging.PagingConfig

class UsersRepository(
    private val remoteSource: UsersRemoteSource
) {
    fun getPagingUsers(keyword: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 10,
            initialLoadSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { UsersPagingDataSource(remoteSource, keyword) }
    )
}