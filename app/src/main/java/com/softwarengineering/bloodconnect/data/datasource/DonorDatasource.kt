package com.softwarengineering.bloodconnect.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.model.Donor

class DonorDatasource(var collectiondonor: CollectionReference) {
     fun registerDonor(
        name: String,
        lastname:String,
        idnumber:String,
        phonenumber:String,
        gender:String,
        blood:String,
        email: String,
        password: String,

    ) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener

                val donor = Donor(
                    donorID = uid,
                    name = name,
                    surname = lastname,
                    birthDate = null,
                    bloodType = "",
                    email = email,
                    phone = "",
                    gender = "",
                    height = 0F,
                    weight = 0F,
                    password = password
                )

                db.collection("donor").document(uid).set(donor)
                    .addOnSuccessListener {
                        Log.d("register", "registerDonor: yes",)}
                    .addOnFailureListener {
                        Log.d("register", "registerDonor: faile ",it)
                    }
            }
            .addOnFailureListener { e ->
                Log.d("register", "registerDonor: ",e)
            }
    }
}
