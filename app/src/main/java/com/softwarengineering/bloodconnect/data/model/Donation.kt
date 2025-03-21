package com.softwarengineering.bloodconnect.data.model

data class Donation(
    var id: String = "",
    var donorID: String = "",
    var recipientID: String = "",
    var hospitalID: String = "",
    var bloodType: String = "",
    var donationTime: String = "",
    var status: String = "pending"  // "pending", "done" olabilir
)
