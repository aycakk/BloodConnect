package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softwarengineering.bloodconnect.data.model.DonationTrackingModel
import com.softwarengineering.bloodconnect.databinding.BottomSheetDonationDetailsBinding

class DonationDetailsBottomSheet(    private val donation: DonationTrackingModel) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDonationDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetDonationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textDonorName.text = donation.donorName
            textDonorId.text = donation.donorIdNumber
            textDonationId.text = donation.donationId
            textDate.text = donation.donationDate
            textStatus.text = donation.donationStatus
            textBloodGroup.text = donation.bloodGroup
            textHospital.text = donation.hospitalName
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}