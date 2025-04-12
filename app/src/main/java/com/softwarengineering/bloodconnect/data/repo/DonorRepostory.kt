package com.softwarengineering.bloodconnect.data.repo

import com.softwarengineering.bloodconnect.data.datasource.DonorDatasource

class DonorRepostory(var donorDatasource: DonorDatasource) {
    fun registerDonor(
        name: String,
        email: String,
        password: String,

    )=donorDatasource.registerDonor(name,email,password)
}