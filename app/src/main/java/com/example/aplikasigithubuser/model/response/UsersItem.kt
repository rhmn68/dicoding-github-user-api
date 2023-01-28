package com.example.aplikasigithubuser.model.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

@Parcelize
data class UsersItem(

	@field:SerializedName("gists_url")
	val gistsUrl: String? = null,

	@field:SerializedName("repos_url")
	val reposUrl: String? = null,

	@field:SerializedName("following_url")
	val followingUrl: String? = null,

	@field:SerializedName("starred_url")
	val starredUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("followers_url")
	val followersUrl: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("subscriptions_url")
	val subscriptionsUrl: String? = null,

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("received_events_url")
	val receivedEventsUrl: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("events_url")
	val eventsUrl: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("site_admin")
	val siteAdmin: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("gravatar_id")
	val gravatarId: String? = null,

	@field:SerializedName("node_id")
	val nodeId: String? = null,

	@field:SerializedName("organizations_url")
	val organizationsUrl: String? = null,

	var userDetail: UserDetailResponse
) : Parcelable{
	companion object {
		val DIFF_UTIL = object : DiffUtil.ItemCallback<UsersItem>() {
			override fun areItemsTheSame(
				oldItem: UsersItem,
				newItem: UsersItem,
			): Boolean = oldItem.id == newItem.id

			override fun areContentsTheSame(
				oldItem: UsersItem,
				newItem: UsersItem,
			): Boolean = oldItem == newItem
		}
	}

}