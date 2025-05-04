package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentCreateBloodRequestBinding
import com.softwarengineering.bloodconnect.viewmodel.HospitalviewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateBloodRequestFragment : Fragment() {
private lateinit var binding:FragmentCreateBloodRequestBinding
private lateinit var viewmodel:HospitalviewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_create_blood_request, container, false)
        // Inflate the layout for this fragment

        binding.buttoncreate.setOnClickListener {
            try {
                val patientname=binding.patientName.text.toString().trim()
                val bloodtype=binding.spinnerbloodtype.selectedItem.toString()
                val unitstext=binding.unitsneeded.text.toString().trim()
                val units=binding.unitsneeded.text.toString().toFloat()
                val note=binding.note.text.toString().trim()
                if ( patientname.isEmpty() || unitstext.isEmpty() ||bloodtype == "Select" || note.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                viewmodel.createRequest(patientname,bloodtype,units,note,onSuccess = {
                    Toast.makeText(requireContext(), "Request created", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                },
                    onFailure = {
                        Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    })
            }
            catch (e:Exception){
                Log.d("CREATEREQUEST", "$e")
            }


        }
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: HospitalviewModel by viewModels()
        viewmodel=tempviewmodel
    }



}