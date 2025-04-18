package com.softwarengineering.bloodconnect.data.model

import com.google.firebase.Timestamp

data class Notification(
    var notificationID: String = "",
    var isRead: Boolean = false,
    var title: String = "",
    var message: String = "",
    var receiverID: String = "",
    var timestamp: Timestamp? = null
)
