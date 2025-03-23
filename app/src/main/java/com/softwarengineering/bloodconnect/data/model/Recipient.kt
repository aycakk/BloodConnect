package com.softwarengineering.bloodconnect.data.model

data class Recipient (
    var id: String = "",
    var name: String = "",
    var surname: String = "",
    var age: Int = 0,
    var bloodType: String = "",
    var gender: String = "",
    var urgency: String = "normal"  // "normal", "high", "critical" gibi seviyeler
)