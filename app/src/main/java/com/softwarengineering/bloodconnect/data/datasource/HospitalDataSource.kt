package com.softwarengineering.bloodconnect.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.data.model.Hospital

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
                    name=name,
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
    }
