package com.softwarengineering.bloodconnect.data.repo

import com.softwarengineering.bloodconnect.data.datasource.DonorDatasource
import com.softwarengineering.bloodconnect.data.model.Donor

class DonorRepository(var donorDatasource: DonorDatasource) {
    fun registerDonor(
        name: String,
        lastname:String,
        idnumber:String,
        phonenumber:String,
        birthdate:String,
        adress: String,
        gender:String,
        blood:String,
        height:Float,
        weight:Float,
        email: String,
        password: String,

    )=donorDatasource.registerDonor(name,lastname,idnumber,phonenumber,birthdate,adress,gender,blood,height,weight,email,password,)

    fun getDonor( onSuccess: (Donor) -> Unit, onFailure: (Exception) -> Unit) =
        donorDatasource.getCurrentDonor( onSuccess, onFailure)
    fun editprofile(
        idnumber:String,
        phonenumber:String,
        gender:String,
        blood:String,
        adress:String,
        birthdate: String
    )=donorDatasource.editprofile(idnumber,phonenumber,gender,blood,adress,birthdate)

}