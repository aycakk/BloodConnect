package com.softwarengineering.bloodconnect.data.model

data class NotificationItem(
    val hospitalName: String,
    val bloodType: String,
    val dateTime: String,
    val isUrgent: Boolean
)
