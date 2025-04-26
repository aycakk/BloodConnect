package com.softwarengineering.bloodconnect.data.model

data class Match(
    //var matchID: String = "",
    var donorID: String = "",
    var donorName: String = "",
    var donorBloodType: String = "",
    var hospitalID: String = "",
    var matchScore: Int = 0,
    var neededBloodType: String = ""// Eşleşme oranı (%0 - %100)
    //var status: String = "matched"  // "matched", "pending", "completed" olabilir
)
