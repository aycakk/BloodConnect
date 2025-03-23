package com.softwarengineering.bloodconnect.data.model

data class Match(
    var matcID: String = "",
    var donorID: String = "",
    var recipientID: String = "",
    var matchScore: Int = 0,  // Eşleşme oranı (%0 - %100)
    var status: String = "matched"  // "matched", "pending", "completed" olabilir
)