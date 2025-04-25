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
            DonationTrackingModel("Ahmet Yılmaz", "İstanbul Kan Merkezi", "10 Eylül 2005"),
            DonationTrackingModel("Ahmet Yılmaz", "Ankara Şehir Hastanesi", "2 Ocak 2018"),
            DonationTrackingModel("Ahmet Yılmaz", "İzmir Kan Bağış Merkezi", "18 Mayıs 2021"),
            DonationTrackingModel("Ahmet Yılmaz", "Bahçelievler Hastanesi", "27 Şubat 2025")

        )

        donationAdapter = DonationTrackingAdapter(dummyDonations)
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
