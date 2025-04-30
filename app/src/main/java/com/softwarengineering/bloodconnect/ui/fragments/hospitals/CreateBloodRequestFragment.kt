package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentCreateBloodRequestBinding
import com.softwarengineering.bloodconnect.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateBloodRequestFragment : Fragment() {

    private var _binding: FragmentCreateBloodRequestBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBloodRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBloodGroupSpinner()
        setupToolbar()

        binding.buttonNotification.setOnClickListener {
            val patientName = binding.patientName.text.toString().trim()
            val bloodType = binding.spinnerbloodtype.selectedItem?.toString()?.trim() ?: ""
            val unitsStr = binding.unitsNeeded.text.toString().trim()
            val notes = binding.note.text.toString().trim()

            if (patientName.isEmpty() || bloodType.isEmpty() || unitsStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val unitsFloat = unitsStr.toFloatOrNull()
            if (unitsFloat == null || unitsFloat <= 0f) {
                Toast.makeText(requireContext(), "Please enter a valid unit amount.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("UnitsDebug", "unitsStr: '$unitsStr', unitsInt: $unitsFloat")

            sendRequestAndNotifyMatchedDonors(
                patientName = patientName,
                bloodType = bloodType,
                units = unitsFloat,
                notes = notes
            )
        }


        binding.buttonCancel.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupBloodGroupSpinner() {
        val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, bloodGroups)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerbloodtype.adapter = adapter
    }

    private fun sendRequestAndNotifyMatchedDonors(
        patientName: String,
        bloodType: String,
        units: Float,
        notes: String,
    ) {
        val hospitalName = SessionManager.currentHospitalName
        val hospitalId = SessionManager.currentHospitalId

        if (hospitalName.isNullOrEmpty() || hospitalId.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Hospital info not found. Please login again.", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("match")
            .whereEqualTo("hospitalID", hospitalId)
            .whereGreaterThanOrEqualTo("matchScore", 70)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(requireContext(), "No matched donors found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val donorIdSet = mutableSetOf<String>()

                for (doc in documents) {
                    val donorId = doc.get("donorID")?.toString()?.trim()
                    if (donorId.isNullOrEmpty()) continue

                    if (!donorIdSet.add(donorId)) continue  // Tekilleştirme

                    val unitsText = if (units % 1f == 0f) units.toInt().toString() else units.toString()
                    val notePart = if (notes.isNotBlank()) " Note: $notes" else ""
                    val message = "$hospitalName requests $unitsText unit(s) of $bloodType blood for $patientName.$notePart"
                    val docId = "$donorId-$hospitalId-${patientName.lowercase().trim()}-${bloodType}-${units}"

                    val notification = hashMapOf(
                        "donorID" to donorId,
                        "patientName" to patientName,
                        "bloodType" to bloodType,
                        "units" to units, // ✅ float olarak gönderiyoruz
                        "notes" to notes,
                        "hospitalName" to hospitalName,
                        "timestamp" to System.currentTimeMillis(),
                        "message" to message
                    )

                    db.collection("notification")
                        .document(docId)
                        .set(notification)
                        .addOnSuccessListener {
                            Log.d("NotificationDebug", "✅ Notification set for $donorId with docId: $docId")
                        }
                        .addOnFailureListener {
                            Log.e("NotificationDebug", "❌ Failed to set notification for $donorId: ${it.message}")
                        }
                }

                Toast.makeText(requireContext(), "Notification sent successfully.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to send notifications: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }




    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarCreateRequest)
        binding.toolbarCreateRequest.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbarCreateRequest.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


