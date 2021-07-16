package com.kujira.hosthomestay.data.model.response


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