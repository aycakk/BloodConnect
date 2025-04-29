package com.softwarengineering.bloodconnect.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.model.BloodInventoryModel

class ManageBloodInventoryVM : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _bloodUnits = MutableLiveData<Map<String, Int>>()
    val bloodUnits: LiveData<Map<String, Int>> = _bloodUnits

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> = _updateStatus

    private val _publishStatus = MutableLiveData<Boolean>()
    val publishStatus: LiveData<Boolean> = _publishStatus


    fun fetchBloodUnits(hospitalName: String) {
        db.collection("blood_inventory")
            .document(hospitalName)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val bloodInventory = document.toObject(BloodInventoryModel::class.java)
                    if (bloodInventory != null) {
                        val units = mapOf(
                            "A+" to bloodInventory.A_plus,
                            "A-" to bloodInventory.A_minus,
                            "B+" to bloodInventory.B_plus,
                            "B-" to bloodInventory.B_minus,
                            "AB+" to bloodInventory.AB_plus,
                            "AB-" to bloodInventory.AB_minus,
                            "O+" to bloodInventory.O_plus,
                            "O-" to bloodInventory.O_minus
                        )
                        _bloodUnits.value = units
                    }
                }
            }
            .addOnFailureListener {
                _bloodUnits.value = emptyMap()
            }
    }


    fun updateBloodUnit(hospitalName: String, bloodType: String, newUnits: Int) {
        val field = bloodType.replace("+", "_plus").replace("-", "_minus")
        db.collection("blood_inventory")
            .document(hospitalName)
            .update(field, newUnits)
            .addOnSuccessListener { _updateStatus.value = true }
            .addOnFailureListener { _updateStatus.value = false }

    }



    fun publishNotification(bloodType: String, hospitalName: String) {
        val notification = hashMapOf(
            "bloodType" to bloodType,
            "hospitalName" to hospitalName,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("notification")
            .add(notification)
            .addOnSuccessListener {
                _publishStatus.value = true
            }
            .addOnFailureListener {
                _publishStatus.value = false
            }
    }
}
