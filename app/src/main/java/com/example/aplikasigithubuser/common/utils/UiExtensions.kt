package com.example.aplikasigithubuser.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun hideSoftKeyboard(context: Context, view: View) {
  val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * VIEW Helper
 */
fun View.visible() {
  visibility = View.VISIBLE
}

fun View.gone() {
  visibility = View.GONE
}

fun View.invisible() {
  visibility = View.INVISIBLE
}

fun View.disabled() {
  isEnabled = false
}

fun View.enabled() {
  isEnabled = true
}

fun SwipeRefreshLayout.showLoading() {
  isRefreshing = true
}

fun SwipeRefreshLayout.hideLoading() {
  isRefreshing = false
}