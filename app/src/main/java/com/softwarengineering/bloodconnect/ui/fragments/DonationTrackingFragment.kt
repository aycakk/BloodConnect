package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.data.model.DonationTrackingModel
import com.softwarengineering.bloodconnect.databinding.FragmentDonationTrackingBinding
import com.softwarengineering.bloodconnect.ui.adapter.DonationTrackingAdapter
import com.softwarengineering.bloodconnect.utils.SessionManager
import java.text.SimpleDateFormat
import java.util.Locale

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
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)

    }

    private fun setupRecyclerView() {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser?.uid ?: return

        val donorName = requireActivity()
            .getSharedPreferences("user_info", 0)
            .getString("donor_name", "Unknown") ?: "Unknown"

        val donationList = mutableListOf<DonationTrackingModel>()

        db.collection("donation")
            .orderBy("donationTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val donationFetchTasks = mutableListOf<com.google.android.gms.tasks.Task<*>>()

                for (document in documents) {
                    val donorId = document.getString("donorID") ?: continue

                    if (donorId == currentUserId) {
                        val donationId = document.id
                        val timestamp = document.getTimestamp("donationTime")
                        val donationDate = timestamp?.toDate()?.let {
                            SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault()).format(it)
                        } ?: ""
                        val bloodType = document.getString("bloodType") ?: ""
                        val status = document.getString("status") ?: ""
                        val hospitalId = document.getString("hospitalID") ?: ""

                        // 🔥 Firestore'dan hospitalName'i al
                        val task = db.collection("hospital").document(hospitalId).get()
                            .addOnSuccessListener { hospitalDoc ->
                                val hospitalName = hospitalDoc.getString("hospitalName") ?: "Unknown Hospital"

                                val donation = DonationTrackingModel(
                                    donorName = donorName,
                                    donorIdNumber = donorId,
                                    donationId = donationId,
                                    donationDate = donationDate,
                                    donationStatus = status,
                                    bloodGroup = bloodType,
                                    hospitalName = hospitalName
                                )

                                donationList.add(donation)
                            }

                        donationFetchTasks.add(task)
                    }
                }

                // 🔁 Tüm hospitalName'ler alındıktan sonra RecyclerView’i kur
                com.google.android.gms.tasks.Tasks.whenAllComplete(donationFetchTasks)
                    .addOnSuccessListener {
                        if (donationList.isEmpty()) {
                            Toast.makeText(requireContext(), "You don't have a donation record yet.", Toast.LENGTH_SHORT).show()
                        }

                        donationAdapter = DonationTrackingAdapter(donationList) { selectedDonation ->
                            val bottomSheet = DonationDetailsBottomSheet(selectedDonation)
                            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
                        }

                        binding.donationRecyclerView.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = donationAdapter
                        }
                    }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load donation records", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
