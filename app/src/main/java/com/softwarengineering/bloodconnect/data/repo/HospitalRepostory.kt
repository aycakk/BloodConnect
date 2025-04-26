package com.softwarengineering.bloodconnect.data.repo

import com.google.firebase.Timestamp
import com.softwarengineering.bloodconnect.data.datasource.HospitalDataSource

class HospitalRepostory(var hospitalDataSource: HospitalDataSource) {
    fun registerhospital(
        name:String,
        email:String,
        phone:String,
        password:String

    )=hospitalDataSource.registerhospital(name,email,phone,password)

    fun createRequest(
        patientName: String,
        bloodType: String,
        units: Float,
        notes: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )=hospitalDataSource.createRequest(patientName,bloodType,units,notes,onSuccess,onFailure)
    fun viewrequest()=hospitalDataSource.viewrewuest()
}