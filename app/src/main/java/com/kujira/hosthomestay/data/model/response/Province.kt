package com.kujira.hosthomestay.data.model.response

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class ProvinceItem(
    val code: Int,
    val codename: String,
    val districts: List<District>,
    val division_type: String,
    val name: String,
    val phone_code: Int
)

data class District(
    val code: Int,
    val codename: String,
    val division_type: String,
    val name: String,
    val short_codename: String,
    val wards: List<Ward>
)

data class Ward(
    val code: Int,
    val codename: String,
    val division_type: String,
    val name: String,
    val short_codename: String
)

data class ProvinceFB(
    val code: Int,
    val name: String,
    val phone_code: Int
)

data class DistrictFB(
    val code: String,
    val name: String
)

data class AddRoomModel(
    var id : String,
    var address: String,
    var typeRoom: String,
    var nameRoom: String,
    var s_room: String,
    var numberSleepRoom: String,
    var convenient: String,
    var introduce: String,
    var imageRoom1: String,
    var imageRoom2: String,
    var status: String,
    var price: String,
    var uid : String
)

@Parcelize
data class AccUser(
    val uid : String,
    val mail : String,
    val phone : String,
    val userName : String,
    val permission : String,
) : Parcelable {

}
