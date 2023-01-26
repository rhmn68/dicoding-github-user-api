package com.example.aplikasigithubuser.common.base

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class BasePagingAdapter<T : Any, VH : RecyclerView.ViewHolder>(
  diffUtil: DiffUtil.ItemCallback<T>,
  private val onCreateViewHolder: ((parent: ViewGroup) -> VH),
  private val onBindViewHolder: ((holder: VH, position: Int, item: T?) -> Unit)
) : PagingDataAdapter<T, VH>(diffUtil) {

  override fun onBindViewHolder(holder: VH, position: Int) =
    onBindViewHolder.invoke(holder, position, getItem(position))

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
    onCreateViewHolder.invoke(parent)

}