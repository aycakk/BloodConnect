package com.softwarengineering.bloodconnect.data.repo

import com.softwarengineering.bloodconnect.data.datasource.HospitalDataSource

class HospitalRepository(var hospitalDataSource: HospitalDataSource) {

    fun registerhospital(
        name: String,
        authname: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        hospitalDataSource.registerhospital(name, authname, email, phone, password, onSuccess, onFailure)
    }



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