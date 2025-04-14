package com.softwarengineering.bloodconnect.data.repo

import com.softwarengineering.bloodconnect.data.datasource.DonorDatasource

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
}