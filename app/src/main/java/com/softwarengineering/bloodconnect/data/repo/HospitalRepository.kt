package com.softwarengineering.bloodconnect.data.repo

import com.softwarengineering.bloodconnect.data.datasource.HospitalDataSource

class HospitalRepository(var hospitalDataSource: HospitalDataSource) {
/*
    fun registerhospital(name: String, email: String, phone: String, password: String): Task<Void> {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: return Tasks.forException(Exception("User not logged in"))

        val hospitalData = hashMapOf(
            "hospitalName" to name,
            "email" to email,
            "phone" to phone,
            "status" to false
        )

        return FirebaseFirestore.getInstance()
            .collection("hospital")
            .document(uid)
            .set(hospitalData)
    }

 */

    fun registerhospital(
        name:String,

        authname:String,
        email:String,
        phone:String,
        password:String

    )=hospitalDataSource.registerhospital(name, authname,email,phone,password)

    fun createRequest(
        patientName: String,
        bloodType: String,
        units: Float,
        notes: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )=hospitalDataSource.createRequest(patientName,bloodType,units,notes,onSuccess,onFailure)
    fun viewrequest()=hospitalDataSource.viewrewuest()
    fun donationform(  idnumber:String,
                      name:String,
                      amount:Float,
                      bloodType: String)=hospitalDataSource.donationform(idnumber,name,amount,bloodType)
}