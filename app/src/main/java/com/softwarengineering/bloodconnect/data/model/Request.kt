package com.softwarengineering.bloodconnect.data.model

import com.google.firebase.Timestamp

data class Request(
    var requestID: String = "",
    var hospitalID: String = "",
    var recipientID: String = "",
    var patientName: String = "",
    var bloodType: String = "",
    var units: Float = 0F,
    var requiredDate: Timestamp? = null,
    var notes: String = "",
    var requestStatus: String = "waiting",  // "waiting", "matched", "completed" olabilir
    var urgencyLevel: String = "normal"  // "normal", "high", "critical" gibi seviyeler
)
