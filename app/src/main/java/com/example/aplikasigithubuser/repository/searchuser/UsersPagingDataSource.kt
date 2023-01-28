package com.example.aplikasigithubuser.repository.searchuser

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.aplikasigithubuser.model.ErrorResponse
import com.example.aplikasigithubuser.model.Resource
import com.example.aplikasigithubuser.model.ResponseException
import com.example.aplikasigithubuser.model.response.UsersItem
import com.example.aplikasigithubuser.model.response.UserDetailResponse

private const val STARTING_PAGE_INDEX = 1

class UsersPagingDataSource(
    private val remoteSource: UsersRemoteSource,
    private val keyword: String = ""
) : PagingSource<Int, UsersItem>() {
    override fun getRefreshKey(state: PagingState<Int, UsersItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersItem> {
        try {
            val position = params.key ?: STARTING_PAGE_INDEX

            return when(val result = remoteSource.getPagingUsers(keyword, position, 20)){
                Resource.Empty -> LoadResult.Error(ResponseException.Empty)
                is Resource.Error -> LoadResult.Error(ResponseException.Error(result.errorResponse))
                is Resource.Success -> {
                    val responseData = result.data
                    val users = responseData.items
                    if (users.isNullOrEmpty()){
                        LoadResult.Error(ResponseException.Empty)
                    }else{
                        for (item in users){
                            loadDetailUser(item.login.toString())?.let {
                                item.userDetail = it
                            }
                        }
                        LoadResult.Page(
                            data = users,
                            prevKey = if (position == STARTING_PAGE_INDEX) null else -1,
                            nextKey = if (users.isEmpty()) null else position + 1
                        )
                    }
                }
                else -> LoadResult.Error(ResponseException.Error(ErrorResponse(message = "Something wrong happened.")))
            }
        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    private suspend fun loadDetailUser(username: String): UserDetailResponse? {
        return when (val result = remoteSource.getDetailUserResult(username)) {
            is Resource.Success -> result.data
            else -> null
        }
    }

}