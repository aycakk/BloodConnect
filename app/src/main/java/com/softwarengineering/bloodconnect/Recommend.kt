package com.softwarengineering.bloodconnect

import android.content.Context
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.data.model.Donation
import java.util.Calendar
import android.location.Location
import com.google.firebase.firestore.GeoPoint

class Recommend() {
    fun scoreDonor(context: Context, donor: Donor, recipientBloodType: String, hospitalAddress: GeoPoint?, donations: List<Donation> ): Float {
        if (donor.birthDate == null || donor.height == 0f || donor.weight == 0f || donor.bloodType == "" || donor.location == null || hospitalAddress == null) {
            return 0f
        }

        val donorBloodType = donor.bloodType
        val bmi = donor.weight / (donor.height * donor.height)

        val gender = if (donor.gender.lowercase() == "male") 1f else 0f
        val smokes = if (donor.isSmoking) 1f else 0f

        val birthCalendar = Calendar.getInstance().apply {
            time = donor.birthDate!!.toDate()
        }
        val today = Calendar.getInstance()

        var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        val healthData = floatArrayOf(age.toFloat(), bmi, smokes, gender)

        val now = Calendar.getInstance()

        val donationDates = donations.mapNotNull { it.donationTime?.toDate() }.sorted()

        val monthsSinceLastDonation = if (donationDates.isNotEmpty()) {
            val lastDonation = Calendar.getInstance().apply { time = donationDates.last() }
            (now.get(Calendar.YEAR) - lastDonation.get(Calendar.YEAR)) * 12 +
                    (now.get(Calendar.MONTH) - lastDonation.get(Calendar.MONTH))
        } else {
            0
        }

        val monthsSinceFirstDonation = if (donationDates.isNotEmpty()) {
            val firstDonation = Calendar.getInstance().apply { time = donationDates.first() }
            (now.get(Calendar.YEAR) - firstDonation.get(Calendar.YEAR)) * 12 +
                    (now.get(Calendar.MONTH) - firstDonation.get(Calendar.MONTH))
        } else {
            0
        }

        val totalDonations = donations.size
        val totalAmount = donations.sumOf { it.amount.toInt() }

        val predData = floatArrayOf(
            monthsSinceLastDonation.toFloat(),
            totalDonations.toFloat(),
            totalAmount.toFloat(),
            monthsSinceFirstDonation.toFloat()
        )

        val results = FloatArray(1)
        Location.distanceBetween(
            hospitalAddress.latitude, hospitalAddress.longitude,
            donor.location.latitude, donor.location.longitude,
            results
        )
        val donorDistance = results[0]

        val bloodCompatibility = mapOf(
            "O-" to listOf("O-"),
            "O+" to listOf("O-", "O+"),
            "A-" to listOf("O-", "A-"),
            "A+" to listOf("O-", "O+", "A-", "A+"),
            "B-" to listOf("O-", "B-"),
            "B+" to listOf("O-", "O+", "B-", "B+"),
            "AB-" to listOf("O-", "A-", "B-", "AB-"),
            "AB+" to listOf("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+")
        )

        val hModel = HealthModel(context)
        val predModel = PredicModel(context)

        var totalScore = 0f

        if (donorBloodType == recipientBloodType) {
            totalScore += 40f
        } else if (bloodCompatibility[recipientBloodType]?.contains(donorBloodType) == true) {
            totalScore += 20f
        } else {
            totalScore = 0f
            return totalScore
        }

        totalScore += 10f / (1f + (donorDistance / 60f)) //half score at 60km

        totalScore += 10f * predModel.predict(predData)

        totalScore += 40f * hModel.predict(healthData)

        hModel.close()
        predModel.close()

        return totalScore

    }
}