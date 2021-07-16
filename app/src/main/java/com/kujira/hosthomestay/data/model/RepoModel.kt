package com.kujira.hosthomestay.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by OpenYourEyes on 11/2/20
 */
@Parcelize
data class RepoModel(
    val id: Int,
    val node_id: String,
    val name: String,
    val full_name: String,
    val owner: Owner,
    var position: Int
) : Parcelable