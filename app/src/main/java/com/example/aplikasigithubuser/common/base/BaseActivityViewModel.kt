package com.example.aplikasigithubuser.common.base

import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

abstract class BaseActivityViewModel<B : ViewBinding, State, VM : BaseViewModel<State>> :
  BaseActivity<B>() {
  /**
   * Use this view model variable if you want use view model
   * */
  protected abstract val viewModel: VM

  override fun onSetupData() {
    super.onSetupData()
    viewModel.state.observe(this, Observer(::setupObserver))
  }

  /**
   * Setup observer for callback data
   * */
  abstract fun setupObserver(state: State)
}