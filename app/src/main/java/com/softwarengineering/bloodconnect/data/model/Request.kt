package com.softwarengineering.bloodconnect.data.model

data class Request(
    var requestID: String = "",
    var hospitalID: String = "",
    var recipientID: String = "",
    var requestStatus: String = "waiting",  // "waiting", "matched", "completed" olabilir
    var urgencyLevel: String = "normal"  // "normal", "high", "critical" gibi seviyeler
)
