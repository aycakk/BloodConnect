package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.DialogEditUnitBinding
import com.softwarengineering.bloodconnect.databinding.FragmentManageBloodInventoryBinding
import com.softwarengineering.bloodconnect.utils.SessionManager
import com.softwarengineering.bloodconnect.viewmodel.ManageBloodInventoryVM

class ManageBloodInventoryFragment : Fragment() {

    private var _binding: FragmentManageBloodInventoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ManageBloodInventoryVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageBloodInventoryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ManageBloodInventoryVM::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeViewModel()
        setupEditButtons()
        setupSpinnerAndPublish()

        SessionManager.currentHospitalName?.let { name ->
            viewModel.fetchBloodUnits(name)
        }



    }

    private fun observeViewModel() {
        viewModel.bloodUnits.observe(viewLifecycleOwner) { units ->
            binding.currentUnitsAPlus.text = units["A+"].toString()
            binding.currentUnitsAMinus.text = units["A-"].toString()
            binding.currentUnitsBPlus.text = units["B+"].toString()
            binding.currentUnitsBMinus.text = units["B-"].toString()
            binding.currentUnitsABPlus.text = units["AB+"].toString()
            binding.currentUnitsABMinus.text = units["AB-"].toString()
            binding.currentUnits0Plus.text = units["O+"].toString()
            binding.currentUnits0Minus.text = units["O-"].toString()
        }

        // ✅ Update işlemi sonrası feedback
        viewModel.updateStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Updated successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Update failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ Publish işlemi sonrası feedback
        viewModel.publishStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Notification published successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to publish notification.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupEditButtons() {
        setupEditListener("A+", binding.editButtonAPlus, binding.currentUnitsAPlus)
        setupEditListener("A-", binding.editButtonAMinus, binding.currentUnitsAMinus)
        setupEditListener("B+", binding.editButtonBPlus, binding.currentUnitsBPlus)
        setupEditListener("B-", binding.editButtonBMinus, binding.currentUnitsBMinus)
        setupEditListener("AB+", binding.editButtonABPlus, binding.currentUnitsABPlus)
        setupEditListener("AB-", binding.editButtonABMinus, binding.currentUnitsABMinus)
        setupEditListener("O+", binding.editButtonOPlus, binding.currentUnits0Plus)
        setupEditListener("O-", binding.editButtonOMinus, binding.currentUnits0Minus)
    }

    private fun setupEditListener(bloodType: String, button: View, textView: TextView) {
        button.setOnClickListener {
            val context = requireContext()
            val dialogBinding = DialogEditUnitBinding.inflate(LayoutInflater.from(context))

            val alertDialog = AlertDialog.Builder(context)
                .setView(dialogBinding.root)
                .create()

            dialogBinding.btnSave.setOnClickListener {
                val inputText = dialogBinding.editTextUnit.text.toString().trim()

                if (inputText.isNotEmpty()) {
                    val newUnits = inputText.toIntOrNull()
                    Log.d("FirestoreUpdateTry", "Trying to update ${SessionManager.currentHospitalName} | BloodType: $bloodType | Units: $newUnits")

                    if (newUnits != null) {
                        if (SessionManager.currentHospitalName != null) {
                            viewModel.updateBloodUnit(
                                SessionManager.currentHospitalName!!,
                                bloodType,
                                newUnits
                            )
                            textView.text = newUnits.toString()
                            alertDialog.dismiss()
                        }
                    } else {
                        Toast.makeText(context, "Please enter a valid number.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Field cannot be empty.", Toast.LENGTH_SHORT).show()
                }
            }


            dialogBinding.btnCancel.setOnClickListener {
                alertDialog.dismiss() // Sadece kapatıyoruz, mesaj vermiyoruz!
            }

            alertDialog.show()
        }
    }

    private fun setupSpinnerAndPublish() {
        val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, bloodGroups)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.bloodGroupSpinner.adapter = adapter

        binding.publishBloodGroupButton.setOnClickListener {
            val selectedBloodGroup = binding.bloodGroupSpinner.selectedItem.toString()
            val hospitalName = SessionManager.currentHospitalName ?: "Unknown Hospital"
            viewModel.publishNotification(selectedBloodGroup, hospitalName)
        }
    }
    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarManageBlood)
        binding.toolbarManageBlood.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbarManageBlood.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
