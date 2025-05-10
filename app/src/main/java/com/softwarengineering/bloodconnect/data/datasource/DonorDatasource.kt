package com.softwarengineering.bloodconnect.data.datasource

import android.icu.text.SimpleDateFormat
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.model.Donor
import com.softwarengineering.bloodconnect.utils.toBase64
import com.softwarengineering.bloodconnect.utils.fromBase64
import java.util.Locale
import java.util.TimeZone

class DonorDatasource(var collectiondonor: CollectionReference) {

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

    ) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener

                  try {
                      // Önce gelen formata göre parse et
                      val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                      val cleanBirthdate = birthdate.trim() // boşlukları temizle
                      val date = inputFormat.parse(cleanBirthdate)

                      val timestamp = date?.let { Timestamp(it) }
                      val donor = Donor(
                          donorID = uid,
                          idnumber=idnumber,
                          name = name,
                          surname = lastname,
                          birthDate =timestamp ,
                          bloodType = blood,
                          address =adress ,
                          email = email,
                          illness ="" ,
                          phone = phonenumber,
                          gender = gender,
                          height =height,
                          weight = weight,)

                      db.collection("donor").document(uid).set(donor)
                          .addOnSuccessListener {
                              Log.d("register", "registerDonor: yes",)}
                          .addOnFailureListener {
                              Log.d("register", "registerDonor: faile ",it)
                          }




                      }

                  catch (e:Exception){

                          Log.d("register", "registerDonor: ",e)
                  }

    }}

    fun getCurrentDonor(
        onSuccess: (Donor) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid.isNullOrEmpty()) {
            onFailure(Exception("User not logged in"))
            return
        }

        collectiondonor.document(uid).get()
            .addOnSuccessListener { doc ->
                val donor = doc.toObject(Donor::class.java)
                if (donor != null) {
                    onSuccess(donor)
                } else {
                    onFailure(Exception("No donor data found"))
                }
            }
            .addOnFailureListener { onFailure(it) }
    }
    fun editprofile(
                    idnumber:String,
                    phonenumber:String,
                    gender:String,
                    blood:String,
                    adress:String,
                    birthdate:String
                    ){
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid.isNullOrEmpty()) {
            Log.e("editprofile", "No current user")
            return
        }

        try {
            // Önce gelen formata göre parse et
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val cleanBirthdate = birthdate.trim() // boşlukları temizle
            val date = inputFormat.parse(cleanBirthdate)

            val timestamp = date?.let { Timestamp(it) }

            val updateprofile = HashMap<String, Any>()
            updateprofile["idnumber"] = idnumber
            updateprofile["phone"] = phonenumber
            updateprofile["gender"] = gender
            updateprofile["bloodType"] = blood
            updateprofile["address"] = adress

            timestamp?.let {
                updateprofile["birthDate"] = it
            }
            Log.d("editprofile", "editprofile: yes")

            collectiondonor.document(uid)
                .update(updateprofile)
                .addOnSuccessListener {
                    Log.d("editprofile", "Profile updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("editprofile", "Failed to update profile", e)
                }
        } catch (e: Exception) {
            Log.e("editprofile", "Error parsing birthdate", e)
        }

    }
}

