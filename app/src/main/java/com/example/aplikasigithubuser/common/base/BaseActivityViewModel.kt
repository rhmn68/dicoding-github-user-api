package com.example.aplikasigithubuser.common.base

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseActivityViewModel<B : ViewBinding, State, VM : ViewModel> :
  BaseActivity<B>() {
  /**
   * Use this view model variable if you want use view model
   * */
  protected lateinit var viewModel: VM

  override fun onSetupData() {
    super.onSetupData()
    val factory = BaseViewModelFactory.newInstance(setupViewModel())
    viewModel = ViewModelProvider(this, factory)[getViewModelClass()]
//    viewModel.state.observe(this, Observer(::setupObserver))
  }

  /**
   * Setup observer for callback data
   * */
  abstract fun setupObserver(state: State)

  /**
   * Add your view model with use case if you have
   * */
  abstract fun setupViewModel(): VM

  /**
   * Convert to view model class
   * */
  @Suppress("UNCHECKED_CAST")
  private fun getViewModelClass(): Class<VM>{
    val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
    return type as Class<VM>
  }
}