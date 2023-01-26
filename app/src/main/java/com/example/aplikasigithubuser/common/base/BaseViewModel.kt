package com.example.aplikasigithubuser.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel<T>(application: Application) : AndroidViewModel(application) {

  private val _state = MutableLiveData<T>()
  protected val context get() = getApplication<Application>()

  val state: LiveData<T> = _state

  fun setState(state: T) {
    _state.value = state!!
  }
}