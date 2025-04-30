package com.softwarengineering.bloodconnect.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.data.repo.DonorRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DonorViewModel @Inject constructor(
   var donorRepository:DonorRepostory
) : ViewModel() {

    private val _donorData = MutableLiveData<Donor>()
    val donorData: LiveData<Donor> get() = _donorData

    fun loadCurrentDonor() {
        donorRepository.getDonor(
            onSuccess = { _donorData.value = it },
            onFailure = { Log.e("DonorVM", "Veri alınamadı: ${it.message}") }
        )
    }
    fun editprofile(
        idnumber:String,
        phonenumber:String,
        gender:String,
        blood:String,
        adress:String,
        birthdate: String
    )=donorRepository.editprofile(idnumber,phonenumber,gender,blood,adress,birthdate)
}
