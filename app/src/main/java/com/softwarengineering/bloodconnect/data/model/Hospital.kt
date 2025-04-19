package com.softwarengineering.bloodconnect.data.model

import com.google.firebase.firestore.GeoPoint

data class Hospital(
    var hospitalID: String = "",
    var hospitalName: String = "",
    var province: String = "",
    var district: String = "",
    var personName: String = "",
    var email: String = "",
    var phone: String = "",
    var password: String = "",
    var address: String = "",
    val location: GeoPoint? = null,
    var status: Boolean = false,  // Onay durumu (true ise aktif)
    var urgentBloodType: String? = null
)
