package com.softwarengineering.bloodconnect.data.model

data class DonationTrackingModel(
    val donorName: String,
    val donorIdNumber: String,
    val donationId: String,
    val donationDate: String,
    val donationStatus: String,
    val bloodGroup: String,
    val hospitalName: String,
)