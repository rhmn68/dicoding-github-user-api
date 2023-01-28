package com.example.aplikasigithubuser.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseViewModelFactory(
    private val viewModel: Any
): ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: BaseViewModelFactory? = null

        fun newInstance(viewModel: Any): BaseViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: BaseViewModelFactory(viewModel)
            }

    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}