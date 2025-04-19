package com.softwarengineering.bloodconnect.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softwarengineering.bloodconnect.data.model.HospitalApiModel
import com.softwarengineering.bloodconnect.databinding.BottomSheetLocationBinding

class LocationBottomSheet(
    private val hospital: HospitalApiModel,
    private val location: LatLng
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetLocationBinding.inflate(inflater, container, false)

        // Bilgileri ekrana yansıt
        binding.tvHospitalName.text = hospital.name
        binding.tvPhoneNumber.text = "Phone: ${hospital.phone.ifBlank { "Unspecified" }}"
        binding.tvAddress.text = "Address: ${hospital.address}"


        binding.tvUrgentBloodType.text = if (hospital.urgentBloodType.isNullOrBlank()) {
            "No urgent need for blood"
        } else {
            "Urgent: ${hospital.urgentBloodType}"
        }

        // Yol tarifi
        binding.btnNavigate.setOnClickListener {
            val gmmIntentUri = Uri.parse("google.navigation:q=${location.latitude},${location.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
