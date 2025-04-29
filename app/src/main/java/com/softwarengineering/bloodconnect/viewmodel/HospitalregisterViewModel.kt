package com.softwarengineering.bloodconnect.viewmodel

import androidx.lifecycle.ViewModel
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






}