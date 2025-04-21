package com.softwarengineering.bloodconnect.data.model

data class HospitalApiModel(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String = "",
    val phone: String = "" , // İsteğe bağlı, çoğu zaman gelmez
    val urgentBloodType: String = "",
    val placeId: String = ""
)
