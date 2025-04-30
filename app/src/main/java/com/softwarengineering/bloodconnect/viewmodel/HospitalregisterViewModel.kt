package com.softwarengineering.bloodconnect.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.repo.DonorRepostory
import com.softwarengineering.bloodconnect.data.repo.HospitalRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class HospitalregisterViewModel@Inject constructor (var hospitalRepostory: HospitalRepostory): ViewModel() {

    var hospitalname=""


    var authname=""
    var phone=""
    var mail=""
    var password=""

    fun registerhospital()=hospitalRepostory.registerhospital(hospitalname,authname,mail,phone,password)

    /*
    fun registerhospital() {
        hospitalRepostory.registerhospital(hospitalname, mail, phone, password)
            .addOnSuccessListener {
                Log.d("Register", "Hospital registration successful")
                initializeBloodInventoryForHospital(hospitalname)
            }
            .addOnFailureListener { e ->
                Log.e("Register", "Hospital registration failed: ${e.message}")
            }


    }


    fun initializeBloodInventoryForHospital(hospitalName: String) {
        Log.d("HospitalDebug", "Hospital name used in Firestore: '$hospitalname'")

        val initialData = hashMapOf(
            "A_plus" to 0,
            "A_minus" to 0,
            "B_plus" to 0,
            "B_minus" to 0,
            "AB_plus" to 0,
            "AB_minus" to 0,
            "O_plus" to 0,
            "O_minus" to 0
        )

        FirebaseFirestore.getInstance()
            .collection("blood_inventory")
            .document(hospitalName)
            .set(initialData)
            .addOnSuccessListener {
                Log.d("InventoryInit", "Initial inventory created for $hospitalName")
            }
            .addOnFailureListener {
                Log.e("InventoryInit", "Failed to create inventory for $hospitalName: ${it.message}")
            }


    }

     */


}