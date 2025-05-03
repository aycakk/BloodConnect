package com.softwarengineering.bloodconnect.data.repo

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.datasource.HospitalDataSource

class HospitalRepository(var hospitalDataSource: HospitalDataSource) {

    fun registerhospital(
        name: String,
        authname:String,
        email: String,
        phone: String,
        password: String): Task<DocumentReference> {
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
            .add(hospitalData)
    }


/*
    fun registerhospital(
        name:String,

        authname:String,
        email:String,
        phone:String,
        password:String

    )=hospitalDataSource.registerhospital(name, authname,email,phone,password)

 */

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