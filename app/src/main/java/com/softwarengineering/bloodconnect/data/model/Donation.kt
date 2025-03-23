package com.softwarengineering.bloodconnect.data.model

import com.google.firebase.Timestamp

data class Donation(
    var id: String = "",
    var donorID: String = "",
    var recipientID: String = "",
    var hospitalID: String = "",
    var bloodType: String = "",
    var amount: Float = 0F,
    var donationTime: Timestamp = Timestamp.now(),
    var status: String = "pending"  // "pending", "done" olabilir
)
