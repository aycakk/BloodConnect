package com.softwarengineering.bloodconnect.data.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

data class Donor (var donorID: String = "",
                  var name: String = "",
                  var surname: String = "",
                  var age: Int = 0,
                  var bloodType: String = "",
                  var email: String = "",
                  var gender: String = "",
                  var height: String = "",
                  var weight:String="",
                  var phone: String = "",
                  var password: String = ""){


}