package com.example.aplikasigithubuser.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

  /**
   * Use this binding variable if you want use view binding
   * */
  lateinit var binding: B

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = setupViewBinding().invoke(layoutInflater)
    setContentView(binding.root)

    onAttach()
    onSetupData()
    onSetupView()
  }

  override fun onStart() {
    super.onStart()
    onAction()
  }


  open fun showLoading(isLoading: Boolean) {
  }

  open fun showDataEmpty() {}
  open fun hideDataEmpty() {}
  open fun showError(
    message: String,
    title: String
  ) {

  }

  open fun hideError() {}
  open fun showSuccess(message: String, title: String) {

  }

  open fun showNotification(
    message: String,
    title: String
  ) {
  }

  /**
   * Add your view binding
   * */
  abstract fun setupViewBinding(): (LayoutInflater) -> B

  /**
   * create new instance object
   * */
  open fun onAttach() {}

  /**
   * setup data
   * */
  open fun onSetupData() {}

  /**
   * setup view
   * */
  abstract fun onSetupView()

  /**
   * Use this function if you have some action in your view
   * */
  open fun onAction() {}
}