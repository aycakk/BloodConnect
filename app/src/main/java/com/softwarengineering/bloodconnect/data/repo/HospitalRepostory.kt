package com.softwarengineering.bloodconnect.data.repo

import com.google.firebase.Timestamp
import com.softwarengineering.bloodconnect.data.datasource.HospitalDataSource

class HospitalRepostory(var hospitalRepostory: HospitalDataSource) {
    fun registerhospital(
        name:String,
        email:String,
        phone:String,
        password:String

    )=hospitalRepostory.registerhospital(name,email,phone,password)

    fun createRequest(
        patientName: String,
        bloodType: String,
        units: Float,
        notes: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )=hospitalRepostory.createRequest(patientName,bloodType,units,notes,onSuccess,onFailure)
}