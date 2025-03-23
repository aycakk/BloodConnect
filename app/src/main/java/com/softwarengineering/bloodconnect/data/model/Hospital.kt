package com.softwarengineering.bloodconnect.data.model

data class Hospital(
    var hospitalID: String = "",
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var address: String = "",
    var password: String = "",
    var status: Boolean = false  // Onay durumu (true ise aktif)
)
