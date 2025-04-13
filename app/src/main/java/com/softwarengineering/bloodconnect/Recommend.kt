package com.softwarengineering.bloodconnect

import android.content.Context
import com.softwarengineering.bloodconnect.data.model.Donor

class Recommend() {
    fun scoreDonor(context: Context, donor: Donor, recipientBloodType: String, hospitalAddress: FloatArray): Float {
        //donorDistance = haversine distance between donor and hospital. use a simpler function later for optimization
        //donorBloodType = donor.bloodType
        //patientBloodType = recipientBloodType
        //bmi = donor.weight / (donor.height * donor.height)
        //healthData = floatArrayOf(donor.age, bmi, donor.smokes, donor.gender)

        //implement database queries after integration for predData

        //sample data until database integration
        val predData = floatArrayOf(2.0f, 5.0f, 1250.0f, 47.0f)
        val healthData = floatArrayOf(20.0f, 30.0f, 0.0f, 1.0f)
        val donorBloodType = "AB+"
        val donorDistance = 0.5f
        val patientBloodType = "AB+"

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

        if (donorBloodType == patientBloodType) {
            totalScore += 40f
        } else if (bloodCompatibility[patientBloodType]?.contains(donorBloodType) == true) {
            totalScore += 20f
        } else {
            totalScore = 0f
            return totalScore
        }

        totalScore += 10f / (1f + (donorDistance / 60f)) //half score at 60km

        totalScore += 10f * predModel.predict(predData)

        totalScore += 40f * hModel.predict(healthData)

        return totalScore

    }
}
