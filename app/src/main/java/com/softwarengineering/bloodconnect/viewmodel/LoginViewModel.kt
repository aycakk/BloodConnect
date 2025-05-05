package com.softwarengineering.bloodconnect.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.data.repo.DonorRepository
import com.softwarengineering.bloodconnect.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor (var donorRepostory: DonorRepository): ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                onFailure(it)
                Log.d("LOGİN", "loginUser: ", it)
            }}

        fun loginhospital(
            email: String,
            password: String,
            onSuccess: () -> Unit,
            onFailure: (Exception) -> Unit,
            onUnapproved:()->Unit

        ) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val uid =
                        FirebaseAuth.getInstance().currentUser?.uid ?: return@addOnSuccessListener

                    // Sadece hospital login sayfasında çağrıldığı için role kontrolü gerekmez
                    FirebaseFirestore.getInstance().collection("hospital").document(uid)
                        .get()
                        .addOnSuccessListener { doc ->
                            val hospitalName = doc.getString("hospitalName") // Firestore'daki alan adı
                            val approved = doc.getBoolean("status") ?: false
                            if (!approved) {
                                FirebaseAuth.getInstance().signOut()
                                onUnapproved()
                                return@addOnSuccessListener
                            }
                            if (!hospitalName.isNullOrBlank()) {
                                SessionManager.currentHospitalName = hospitalName.trim()
                                onSuccess()
                            } else {
                                onFailure(Exception("Hospital name is missing in Firestore."))
                            }
                        }
                        .addOnFailureListener { onFailure(it) }
                }
        }
    }
