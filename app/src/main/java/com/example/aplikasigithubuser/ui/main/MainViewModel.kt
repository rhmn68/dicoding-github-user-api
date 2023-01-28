package com.example.aplikasigithubuser.ui.main

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.aplikasigithubuser.common.base.BaseViewModel
import com.example.aplikasigithubuser.repository.searchuser.UsersRepository

class MainViewModel constructor(
    private val repository: UsersRepository
) : ViewModel(){

    private val keyword = MutableLiveData<String>()

    fun setKeyword(keyword: String){
        this.keyword.value = keyword
    }

    private fun fetchUsers(keyword: String) = repository.getPagingUsers(keyword).flow.cachedIn(viewModelScope)

    fun getUsers() = Transformations.switchMap(keyword){
        fetchUsers(it).asLiveData(viewModelScope.coroutineContext)
    }
}