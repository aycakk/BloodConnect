package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentManageBloodInventoryBinding
import com.softwarengineering.bloodconnect.utils.SessionManager

class ManageBloodInventoryFragment : Fragment() {

    private var _binding: FragmentManageBloodInventoryBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageBloodInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupPublishButtons()
    }

    private fun setupPublishButtons() {
        setupPublishListener("A+", binding.publishButtonAPlus)
        setupPublishListener("A-", binding.publishButtonAMinus)
        setupPublishListener("B+", binding.publishButtonBPlus)
        setupPublishListener("B-", binding.publishButtonBMinus)
        setupPublishListener("AB+", binding.publishButtonABPlus)
        setupPublishListener("AB-", binding.publishButtonABMinus)
        setupPublishListener("O+", binding.publishButtonOPlus)
        setupPublishListener("O-", binding.publishButtonOMinus)
    }

    private fun setupPublishListener(
        bloodType: String,
        button: View
    ) {
        button.setOnClickListener {
            val hospitalName = SessionManager.currentHospitalName ?: "Unknown Hospital"
            publishNotification(bloodType, hospitalName)
        }
    }


    private fun publishNotification(bloodType: String, hospitalName: String) {
        val db = FirebaseFirestore.getInstance()
        val notification = hashMapOf(
            "bloodType" to bloodType,
            "hospitalName" to hospitalName,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("notification")
            .add(notification)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "$bloodType notification published at $hospitalName!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to publish: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarManageBlood)
        binding.toolbarManageBlood.title = "Donation Tracking"
        binding.toolbarManageBlood.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbarManageBlood.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
