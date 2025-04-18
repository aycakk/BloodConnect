package com.softwarengineering.bloodconnect.data.model

import com.google.firebase.Timestamp

data class Donation(
    var donationID: String = "",
    var donorID: String = "",
    var recipientID: String = "",
    var hospitalID: String = "",
    var bloodType: String = "",
    var donationTime: Timestamp? = null,
    var amount: Float = 0F,
    var status: String = "pending"  // "pending", "done" olabilir
)
