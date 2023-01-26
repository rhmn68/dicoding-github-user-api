package com.example.aplikasigithubuser.common.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T, VH : RecyclerView.ViewHolder>(
  private val onCreateViewHolder: ((parent: ViewGroup) -> VH),
  private val onBindViewHolder: ((holder: VH, position: Int, item: T) -> Unit)
) : RecyclerView.Adapter<VH>() {

  private var items = mutableListOf<T>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
    onCreateViewHolder.invoke(parent)

  override fun onBindViewHolder(holder: VH, position: Int) {
    onBindViewHolder.invoke(holder, position, items[position])
  }

  override fun getItemCount(): Int = items.size

  private val itemSize = items.size

  fun remove(item: T) {
    val index = items.indexOfFirst { it == item }

    items.removeAt(index)
    notifyItemRemoved(index)
    notifyItemRangeChanged(index, itemSize)
  }

  fun removeFromPosition(index: Int) {
    items.removeAt(index)
    notifyItemRemoved(index)
    notifyItemRangeChanged(index, itemSize, items)
  }


  fun add(item: T) {
    items.add(item)
    notifyItemInserted(items.size)
  }

  fun submitAllData(items: List<T>) {
    this.items = items as MutableList<T>
    notifyDataSetChanged()
  }

  fun updateData(index: Int, item: T) {
    if (isWithinRange(index)) {
      items[index] = item
      notifyItemChanged(index)
    }
  }

  fun updateAllData(items: List<T>) {
    this.items = items as MutableList<T>
    notifyDataSetChanged()
  }

  fun getAllData() = items

  private fun isWithinRange(index: Int): Boolean {
    return index >= 0 && index < items.size
  }
}