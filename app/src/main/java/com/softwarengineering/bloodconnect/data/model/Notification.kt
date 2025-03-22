package com.softwarengineering.bloodconnect.data.model

import com.google.firebase.Timestamp

data class Notification (
    var id: String = "",
    var isRead: Boolean = false,
    var message: String = "",
    var receiverID: String = "",
    var timestamp: Timestamp = Timestamp.now(),
    var title: String = ""
)