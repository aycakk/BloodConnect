package com.softwarengineering.bloodconnect.data.datasource

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.data.model.Hospital
import com.softwarengineering.bloodconnect.data.model.Request

class HospitalDataSource(var collectionhospital :CollectionReference) {
    fun registerhospital(
        name:String,
        email:String,
        phone:String,
        password:String

    ){
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener

                val hospital = Hospital(
                    hospitalID = uid,
                    hospitalName=name,
                     email=email,
                     phone=phone,
                     password = "",
                    address = "",

                )

                db.collection("hospital").document(uid).set(hospital)
                    .addOnSuccessListener {
                        Log.d("register", "registerhospital: yes",)}
                    .addOnFailureListener {
                        Log.d("register", "registerhospital: faile ",it)
                    }
            }
            .addOnFailureListener { e ->
                Log.d("register", "registerhospital: ",e)
            }
    }

    fun createRequest(
        patientName: String,
        bloodType: String,
        units: Float,
        notes: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val hospitalID = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val request = Request(
            "",  // Firestore otomatik ID atayacak
            hospitalID,
            "",
             patientName,
            bloodType,
             units,
           Timestamp.now(),
             notes,
            "",
            ""
        )

        FirebaseFirestore.getInstance().collection("request")
            .add(request)
            .addOnSuccessListener {
                Log.d("createRequest", "Kan talebi başarıyla oluşturuldu.")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("createRequest", "Kan talebi oluşturulamadı", e)
                onFailure(e)
            }
    }

}
