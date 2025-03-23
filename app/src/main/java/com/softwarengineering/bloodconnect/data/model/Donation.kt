package com.softwarengineering.bloodconnect.data.model

data class Donation(
    var donationID: String = "",
    var donorID: String = "",
    var recipientID: String = "",
    var hospitalID: String = "",
    var bloodType: String = "",
    var donationTime: String = "",
    var amount:Int=0,
    var status: String = "pending"  // "pending", "done" olabilir
)
