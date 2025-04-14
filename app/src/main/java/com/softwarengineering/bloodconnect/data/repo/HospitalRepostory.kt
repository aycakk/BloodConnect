package com.softwarengineering.bloodconnect.data.repo

import com.softwarengineering.bloodconnect.data.datasource.HospitalDataSource

class HospitalRepostory(var hospitalRepostory: HospitalDataSource) {
    fun registerhospital(
        name:String,
        email:String,
        phone:String,
        password:String

    )=hospitalRepostory.registerhospital(name,email,phone,password)
}