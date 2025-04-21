package com.softwarengineering.bloodconnect.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.softwarengineering.bloodconnect.data.repo.DonorRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(var donorRepostory: DonorRepostory):ViewModel() {
    fun registerDonor(

    ){donorRepostory.registerDonor(name,lastname,idnumber,phonenumber,gender,blood,email,password,)
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


}