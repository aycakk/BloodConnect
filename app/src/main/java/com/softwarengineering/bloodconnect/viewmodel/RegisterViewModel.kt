package com.softwarengineering.bloodconnect.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.softwarengineering.bloodconnect.data.repo.DonorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(var donorRepository: DonorRepository):ViewModel() {
    fun registerDonor(

    ){donorRepository.registerDonor(name,lastname,idnumber,phonenumber,birthdate,adress,gender,blood,email,password)
        Log.d("registerdonor", "$name,$idnumber")}
    var name :String= ""
    var lastname:String = ""
    var idnumber:String= ""
    var phonenumber:String= ""
    var adress:String= ""
    var gender:String= ""
    var blood:String= ""
    var email: String= ""
    var password: String= ""
    var birthdate:String=""



}