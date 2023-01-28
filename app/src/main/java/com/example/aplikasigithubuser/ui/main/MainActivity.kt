package com.example.aplikasigithubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.common.base.BaseActivity
import com.example.aplikasigithubuser.common.base.BaseActivityViewModel
import com.example.aplikasigithubuser.common.utils.observe
import com.example.aplikasigithubuser.databinding.ActivityMainBinding
import com.example.aplikasigithubuser.di.Injection
import com.example.aplikasigithubuser.model.ErrorResponse
import com.example.aplikasigithubuser.model.ResponseException
import com.example.aplikasigithubuser.model.response.UsersItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import setAdapter

class MainActivity : BaseActivityViewModel<ActivityMainBinding, MainState, MainViewModel>() {

  private val usersAdapter by lazy { setAdapter(UsersItem.DIFF_UTIL, ::createUsersViewHolder) }

  override fun setupViewBinding(): (LayoutInflater) -> ActivityMainBinding =
    ActivityMainBinding::inflate

  override fun onSetupData() {
    super.onSetupData()
    run {
      observe(viewModel.getUsers()){
        usersAdapter.submitData(lifecycle, it)
      }
      lifecycleScope.launchWhenCreated {
        val loadStateFlow = usersAdapter.loadStateFlow
        loadStateFlow.map { it.refresh }
          .distinctUntilChanged()
          .collect{ renderLoadState(it) }
      }
    }
  }

  override fun setupViewModel(): MainViewModel = MainViewModel(
    Injection.provideMainRepository()
  )

  override fun setupObserver(state: MainState) {

  }

  override fun onSetupView() {
    binding.rvUsers.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = usersAdapter
    }
  }

  private fun showEmptyState() {

  }

  private fun renderLoadState(state: LoadState) {
    when(state){
      is LoadState.NotLoading -> {
        showLoading(false)
        val itemCount = usersAdapter.itemCount
        if (itemCount > 0){
          showDataEmpty()
        }
        binding.rvUsers.scrollToPosition(0)
      }
      LoadState.Loading -> showLoading(true)
      is LoadState.Error -> {
        showLoading(false)
        val error = state.error
        if (error is ResponseException.Error){
          when(error.errorResponse.type){
            ErrorResponse.Type.GENERAL -> {

            }
            ErrorResponse.Type.NO_DATA -> showEmptyState()
            else -> {

            }
          }
        } else showEmptyState()
      }
    }
  }

  override fun onAction() {
    super.onAction()

    viewModel.setKeyword("")
  }

  /**
   * Create ViewHolder
   */
  private fun createUsersViewHolder(
    parent: ViewGroup
  ) = UsersViewHolder.create(parent).setOnclick(::usersOnClick)

  /**
   * ViewHolder OnClick
   */
  private fun usersOnClick(
    item: UsersItem
  ){

  }
}