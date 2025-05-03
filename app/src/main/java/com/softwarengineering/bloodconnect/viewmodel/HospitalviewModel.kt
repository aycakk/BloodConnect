package com.softwarengineering.bloodconnect.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softwarengineering.bloodconnect.data.model.Request
import com.softwarengineering.bloodconnect.data.repo.HospitalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HospitalviewModel@Inject constructor (var hospitalRepository: HospitalRepository): ViewModel()  {
    var requestlist=MutableLiveData<List<Request>>()
    init {
        viewrequest()
    }
    fun createRequest(
        patientName: String,
        bloodType: String,
        units: Float,
        notes: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )=hospitalRepository.createRequest(patientName,bloodType,units,notes,onSuccess,onFailure)
    fun viewrequest(){
        requestlist=hospitalRepository.viewrequest()}

}