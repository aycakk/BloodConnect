package com.softwarengineering.bloodconnect.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.softwarengineering.bloodconnect.data.model.Request
import com.softwarengineering.bloodconnect.data.repo.HospitalRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class HospitalviewModel@Inject constructor (var hospitalRepostory: HospitalRepostory): ViewModel()  {
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
    )=hospitalRepostory.createRequest(patientName,bloodType,units,notes,onSuccess,onFailure)
    fun viewrequest(){
        requestlist=hospitalRepostory.viewrequest()}

}