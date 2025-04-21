package com.softwarengineering.bloodconnect.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Donor(
    var donorID: String = "",
    var idnumber:String="",
    var name: String = "",
    var surname: String = "",
    var birthDate: Timestamp? = null,
    var citizenID: String = "",
    var phone: String = "",
    var address: String = "",
    val location: GeoPoint? = null,
    var isSmoking: Boolean = false,
    var haveIllness: Boolean = false,
    var illness: String = "",
    var mail: String = "",
    var bloodType: String = "",
    var email: String = "",
    var gender: String = "",
    var height: Float = 0F,
    var weight: Float = 0F,
    var password: String = ""
)