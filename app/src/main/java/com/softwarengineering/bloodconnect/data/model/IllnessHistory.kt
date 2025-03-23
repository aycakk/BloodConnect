package com.softwarengineering.bloodconnect.data.model

import com.google.firebase.Timestamp

data class IllnessHistory (
    var illnessName: String = "",
    var startDate: Timestamp = Timestamp.now(),
    var endDate: Timestamp = Timestamp.now()
)