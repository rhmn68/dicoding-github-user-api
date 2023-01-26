package com.example.aplikasigithubuser.common.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T, B : ViewBinding> internal constructor(private val _binding: B) :
  RecyclerView.ViewHolder(_binding.root) {

  protected val binding: B get() = _binding
  lateinit var action: (View, T) -> Unit
  lateinit var actionItem: (T) -> Unit
  lateinit var actionPosition: (Int) -> Unit
  lateinit var actionItemPosition: (Int, T) -> Unit

  abstract fun bindItem(position: Int, item: T)

  fun setOnclick(action: (View, T) -> Unit) = apply { this.action = action }
  fun setOnclick(action: (T) -> Unit) = apply { actionItem = action }
  fun setOnclickPosition(action: (Int) -> Unit) = apply { actionPosition = action }
  fun setOnclickItemPosition(action: (Int, T) -> Unit) = apply { actionItemPosition = action }

  protected fun View.setOnClickListener(item: T) {
    setOnClickListener { view ->
      if (::action.isInitialized) action(view, item)
      if (::actionItem.isInitialized) actionItem(item)
      if (::actionPosition.isInitialized) actionPosition(absoluteAdapterPosition)
      if (::actionItemPosition.isInitialized) actionItemPosition(absoluteAdapterPosition, item)
    }
  }
}