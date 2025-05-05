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
        password:String,onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit

    )=hospitalDataSource.registerhospital(name, authname,email,phone,password,onSuccess,onFailure)

    fun createRequest(
        patientName: String,
        bloodType: String,
        units: Float,
        notes: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )=hospitalDataSource.createRequest(patientName,bloodType,units,notes,onSuccess,onFailure)
    fun viewrequest()=hospitalDataSource.viewrequest()
    fun donationform(  idnumber:String,
                      name:String,
                      amount:Float,
                      bloodType: String,
                       onSuccess: () -> Unit,
                       onFailure: (String) -> Unit)=hospitalDataSource.donationform(idnumber,name,amount,bloodType,onSuccess,onFailure)
}