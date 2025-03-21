package com.softwarengineering.bloodconnect.data.model

data class Donor (var id: String = "",
                  var name: String = "",
                  var surname: String = "",
                  var age: Int = 0,
                  var bloodType: String = "",
                  var email: String = "",
                  var gender: String = "",
                  var height: String = "",
                  var phone: String = "",
                  var password: String = ""){
}