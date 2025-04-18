package com.softwarengineering.bloodconnect.data.repo

import com.softwarengineering.bloodconnect.data.datasource.DonorDatasource
import com.softwarengineering.bloodconnect.data.model.Donor

class DonorRepostory(var donorDatasource: DonorDatasource) {
    fun registerDonor(
        name: String,
        lastname:String,
        idnumber:String,
        phonenumber:String,
        gender:String,
        blood:String,
        email: String,
        password: String,

    )=donorDatasource.registerDonor(name,lastname,idnumber,phonenumber,gender,blood,email,password)

    fun getDonor( onSuccess: (Donor) -> Unit, onFailure: (Exception) -> Unit) =
        donorDatasource.getCurrentDonor( onSuccess, onFailure)

}