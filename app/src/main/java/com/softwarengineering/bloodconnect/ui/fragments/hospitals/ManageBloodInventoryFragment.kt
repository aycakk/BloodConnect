package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentManageBloodInventoryBinding

class ManageBloodInventoryFragment : Fragment() {

    private var _binding: FragmentManageBloodInventoryBinding? = null
    private val binding get() = _binding!!

    // Hafızada tuttuğumuz veri
    private val bloodInventoryMap = mutableMapOf<String, String>()

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
        setupPublishListener("A+", binding.availableUnitsAPlus.text, binding.publishButtonAPlus)
        setupPublishListener("A-", binding.availableUnitsAMinus.text, binding.publishButtonAMinus)
        setupPublishListener("B+", binding.availableUnitsBPlus.text, binding.publishButtonBPlus)
        setupPublishListener("B-", binding.availableUnitsBMinus.text, binding.publishButtonBMinus)
        setupPublishListener("AB+", binding.availableUnitsABPlus.text, binding.publishButtonABPlus)
        setupPublishListener("AB-", binding.availableUnitsABMinus.text, binding.publishButtonABMinus)
        setupPublishListener("O+", binding.availableUnitsOPlus.text, binding.publishButtonOPlus)
        setupPublishListener("O-", binding.availableUnitsOMinus.text, binding.publishButtonOMinus)
    }

    private fun setupPublishListener(
        bloodType: String,
        editable: Editable?,
        button: View
    ) {
        button.setOnClickListener {
            val units = editable?.toString()?.trim() ?: ""

            if (units.isNotBlank()) {
                bloodInventoryMap[bloodType] = units

                Toast.makeText(
                    requireContext(),
                    "$bloodType updated: $units units available!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter available units for $bloodType.",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
