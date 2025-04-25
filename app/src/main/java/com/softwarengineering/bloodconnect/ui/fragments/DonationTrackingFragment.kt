package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.data.model.DonationTrackingModel
import com.softwarengineering.bloodconnect.databinding.FragmentDonationTrackingBinding
import com.softwarengineering.bloodconnect.ui.adapter.DonationTrackingAdapter

class DonationTrackingFragment : Fragment() {

    private var _binding: FragmentDonationTrackingBinding? = null
    private val binding get() = _binding!!

    private lateinit var donationAdapter: DonationTrackingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonationTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarDonationTracking)
        binding.toolbarDonationTracking.title = "Donation Tracking"
        binding.toolbarDonationTracking.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbarDonationTracking.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
    }

    private fun setupRecyclerView() {
        val dummyDonations = listOf(
            DonationTrackingModel(
                donorName = "Ahmet Yılmaz",
                donorIdNumber = "1234567890",
                donationId = "DON-001",
                donationDate = "10 Nisan 2025",
                donationStatus = "Pending",
                bloodGroup = "A+",
                hospitalName = "İstanbul Kan Merkezi",
            ),
            DonationTrackingModel(
                donorName = "Ahmet Yılmaz",
                donorIdNumber = "0987654321",
                donationId = "DON-002",
                donationDate = "15 Nisan 2025",
                donationStatus = "Completed",
                bloodGroup = "O-",
                hospitalName = "Ankara Şehir Hastanesi",
            ),
            DonationTrackingModel(
                donorName = "Ahmet Yılmaz",
                donorIdNumber = "5678901234",
                donationId = "DON-003",
                donationDate = "20 Nisan 2025",
                donationStatus = "Canceled",
                bloodGroup = "B+",
                hospitalName = "İzmir Kan Bağış Merkezi",
            )

        )

        donationAdapter = DonationTrackingAdapter(dummyDonations) { selectedDonation ->
            val bottomSheet = DonationDetailsBottomSheet(selectedDonation)
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        binding.donationRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = donationAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
