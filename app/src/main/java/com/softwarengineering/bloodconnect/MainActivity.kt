package com.softwarengineering.bloodconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.model.Donation
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.data.model.Hospital
import com.softwarengineering.bloodconnect.data.model.Match
import com.softwarengineering.bloodconnect.data.model.Request
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*val hospital = Hospital(
             "HOSP_001",
            hospitalName = "Hospital1",
            address = "İstanbul",
            email = "h1@gmail.com",
            phone = "5555555555",
            password = "1234",
            status = true
        )

        val donor = Donor(
            "11111111111",
            "Zeki",
             "Bayar",
            bloodType = "A+",
            mail = "test@gmail.com",
            phone = "5555555555",
            gender = "male"
        )

        val request =Request(
            hospitalID = "HOSP_001",
            recipientID = "22222222222",
            requestStatus = "waiting",
            urgencyLevel = "High"
        )

        val donation = Donation(
            bloodType = "O+",
            donorID = "22222222222",
            hospitalID = "HOSP_001",
            recipientID = "11111111111",
            status = "done"
        )

        val match = Match(
            donorID = "22222222222",
            recipientID = "11111111111",
            matchScore = 95,
            status = "matched"
        )



        testDonorAccess()
        testHospitalAccess()
        testMyRequests()
        testMyDonations()
        testMyMatches()








    }
    fun addHospital(hospital: Hospital) {
        val db = FirebaseFirestore.getInstance()
        db.collection("hospital").document(hospital.hospitalID)
            .set(hospital)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Hastane eklendi: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Hata oluştu: ${e.message}")
            }
    }
    fun addDonation(donation: Donation) {
        val db = FirebaseFirestore.getInstance()
        db.collection("donation").document(donation.donationID)
            .set(donation)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Bağış eklendi: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Hata oluştu: ${e.message}")
            }
    }
    fun addMatch(match: Match) {
        val db = FirebaseFirestore.getInstance()
        db.collection("match").document(match.matchID)
            .set(match)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Eşleştirme eklendi: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Hata oluştu: ${e.message}")
            }
    }
    fun addBloodRequest(request: Request) {
        val db = FirebaseFirestore.getInstance()
        db.collection("request").document(request.requestID)
            .set(request)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Bağış talebi eklendi: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Hata oluştu: ${e.message}")
            }
    }
    fun addDonor(donor: Donor) {
        val db = FirebaseFirestore.getInstance()
        db.collection("donor").document(donor.donorID)
            .set(donor)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Donör eklendi: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Hata oluştu: ${e.message}")
            }
    }
    fun testDonorAccess() {


        val db = FirebaseFirestore.getInstance()
        val donorID = "11111111111" // test verisindeki ID
        Log.d("TEST-CONTROL", "Fonksiyon başladı")

        db.collection("donor").document(donorID).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    Log.d("TEST", "Donor verisi çekildi: ${doc.id}")
                } else {
                    Log.e("TEST", "Donor verisi bulunamadı.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("TEST", "Donor erişim hatası: ${e.message}")
            }
    }

    fun testHospitalAccess() {
        Log.d("TEST-CONTROL", "Fonksiyon başladı")

        val db = FirebaseFirestore.getInstance()
        val hospitalID = "HOSP_001"

        db.collection("hospital").document(hospitalID).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val status = doc.getBoolean("status") ?: false
                    if (status) {
                        Log.d("TEST", "Hastane onaylı. Veriler: ${doc.id}")
                    } else {
                        Log.e("TEST", "Hastane onaylı değil. Çıkış yapılmalı.")
                    }
                } else {
                    Log.e("TEST", "Hastane kaydı bulunamadı.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("TEST", "Hastane erişim hatası: ${e.message}")
            }
    }

    fun testMyDonations() {
        Log.d("TEST-CONTROL", "Fonksiyon başladı")

        val db = FirebaseFirestore.getInstance()
        val donorID = "11111111111" // kendi kullanıcı UID

        db.collection("donation").whereEqualTo("donorID", donorID).get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    Log.d("TEST", "Bağış: ${doc.data}")
                }
            }
            .addOnFailureListener { e ->
                Log.e("TEST", "Donation erişim hatası: ${e.message}")
            }
    }

    fun testMyRequests() {
        Log.d("TEST-CONTROL", "Fonksiyon başladı")

        val db = FirebaseFirestore.getInstance()
        val hospitalID = "HOSP_001"

        db.collection("request").whereEqualTo("hospitalID", hospitalID).get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    Log.d("TEST", "Talep: ${doc.data}")
                }
            }
            .addOnFailureListener { e ->
                Log.e("TEST", "Request erişim hatası: ${e.message}")
            }
    }

    fun testMyMatches() {
        Log.d("TEST-CONTROL", "Fonksiyon başladı")

        val db = FirebaseFirestore.getInstance()
        val donorID = "11111111111"

        db.collection("match").whereEqualTo("donorID", donorID).get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    Log.d("TEST", "Eşleşme: ${doc.data}")
                }
            }
            .addOnFailureListener { e ->
                Log.e("TEST", "Match erişim hatası: ${e.message}")
            }*/
    }










}