package com.kujira.hosthomestay.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class TestModel {

}

data class GitResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: MutableList<RepoModel>
)

@Parcelize
data class Owner(
    val login: String,
    val id: Int,
    val node_id: String,
    val avatar_url: String,
    val gravatar_id: String,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val starred_url: String,
    val subscriptions_url: String,
    val organizations_url: String,
    val repos_url: String,
    val events_url: String,
    val received_events_url: String,
    val type: String,
    val site_admin: Boolean
) : Parcelable

@Parcelize
data class License(
    val key: String,
    val name: String,
    val spdx_id: String,
    val url: String,
    val node_id: String
) : Parcelable