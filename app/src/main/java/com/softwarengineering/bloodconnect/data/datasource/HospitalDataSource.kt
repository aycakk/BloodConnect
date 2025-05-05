package com.softwarengineering.bloodconnect.data.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.model.Donation
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.data.model.Hospital
import com.softwarengineering.bloodconnect.data.model.Request

class HospitalDataSource(var collectionhospital :CollectionReference) {

    var requestlist = MutableLiveData<List<Request>>()
    fun registerhospital(
        name: String,
        authname: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener

                val hospitalData = hashMapOf(
                    "hospitalID" to uid,
                    "hospitalName" to name,
                    "personName" to authname,
                    "email" to email,
                    "phone" to phone,
                    "password" to "",
                    "status" to false
                )

                db.collection("hospital").document(uid)
                    .set(hospitalData)
                    .addOnSuccessListener {
                        Log.d("register", "Hospital kaydı başarılı")
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.e("register", "Firestore kaydı başarısız", e)
                        onFailure(e)
                    }
            }
            .addOnFailureListener { e ->
                Log.e("register", "FirebaseAuth kayıt başarısız", e)
                onFailure(e)
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

    fun viewrequest(): MutableLiveData<List<Request>> {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val collectionRequest = FirebaseFirestore.getInstance().collection("request")
            Log.d("user", "userıd: $userId")
            collectionRequest.whereEqualTo("hospitalID", userId)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.e("FirestoreError", "Error getting tasks", error)
                        return@addSnapshotListener
                    }
                    if (value != null) {
                        val list = ArrayList<Request>()
                        for (d in value.documents) {
                            val request = d.toObject(Request::class.java)
                            if (request != null) {
                                request.hospitalID = d.id
                                Log.d("FirestoreData", "Document ID: ${d.id}, Data: ${d.data}")
                                list.add(request)
                            }

                        }

                        requestlist.value = list

                    }
                }}
            return requestlist


    }
    fun donationform(
        idnumber: String,
        name: String,
        amount: Float,
        bloodType: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val hospitalID = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val firestore = FirebaseFirestore.getInstance()
        val usersRef = firestore.collection("donor")
        val donationRef = firestore.collection("donation").document()
        val donationID = donationRef.id

        usersRef.whereEqualTo("idnumber", idnumber).get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val donorDoc = querySnapshot.documents[0]
                    val donorID = donorDoc.id

                    val donationData = Donation(
                        hospitalID = hospitalID,
                        donationID = donationID,
                        donorID = donorID,
                        donationTime = Timestamp.now(),
                        bloodType = bloodType,
                        amount = amount
                    )

                    donationRef.set(donationData)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onFailure("Kayıt sırasında hata: ${it.message}") }

                } else {
                    onFailure("No user found matching this ID number")
                }
            }
            .addOnFailureListener {
                onFailure("Sorgu başarısız: ${it.message}")
            }
    }



}


