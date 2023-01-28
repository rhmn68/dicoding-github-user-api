package com.example.aplikasigithubuser.ui.main

import android.view.ViewGroup
import com.example.aplikasigithubuser.common.base.BaseViewHolder
import com.example.aplikasigithubuser.databinding.ItemUserBinding
import com.example.aplikasigithubuser.model.response.UsersItem
import inflateBinding

class UsersViewHolder(binding: ItemUserBinding) :
BaseViewHolder<UsersItem, ItemUserBinding>(binding){

    companion object{
        fun create(parent: ViewGroup): UsersViewHolder =
            UsersViewHolder(parent.inflateBinding(ItemUserBinding::inflate))
    }

    override fun bindItem(position: Int, item: UsersItem) {
        binding.apply {
            tvUsername.text = item.login
            tvFollowersCount.text = item.userDetail.followers.toString()
            tvFollowingCount.text = item.userDetail.following.toString()
        }
        itemView.setOnClickListener(item)
    }
}