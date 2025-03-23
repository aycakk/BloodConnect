package com.softwarengineering.bloodconnect.data.model

data class Donor (var id: String = "",
                  var name: String = "",
                  var surname: String = "",
                  var age: Int = 0,
                  var bloodType: String = "",
                  var email: String = "",
                  var gender: String = "",
                  var height: Float = 0F,
                  var weight: Float = 0F,
                  var phone: String = "",
                  var password: String = "",
                  var isSmoking: Boolean = false
    )