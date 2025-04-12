package com.softwarengineering.bloodconnect.viewmodel

import androidx.lifecycle.ViewModel
import com.softwarengineering.bloodconnect.data.repo.DonorRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(var donorRepostory: DonorRepostory):ViewModel() {
    fun registerDonor(
        name: String,
        email: String,
        password: String,

    )=donorRepostory.registerDonor(name,email,password,)
}