package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentHospitalRegister1Binding
import com.softwarengineering.bloodconnect.viewmodel.HospitalregisterViewModel
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import com.softwarengineering.bloodconnect.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalRegister1Fragment : Fragment() {
    private lateinit var binding:FragmentHospitalRegister1Binding
    private lateinit var registerViewModel: HospitalregisterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_hospital_register1, container, false)
        // Inflate the layout for this fragment
        binding.buttonContinue.setOnClickListener {
            registerViewModel.hospitalname=binding.hospitalname.text.toString()
            registerViewModel.private=binding.privatechoose.toString()
            registerViewModel.province=binding.province.text.toString()
            registerViewModel.code=binding.code.text.toString()
            Navigation.findNavController(it).navigate(R.id.action_hospitalRegister1Fragment_to_hospitalRegister2Fragment)
        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: HospitalregisterViewModel by viewModels()
        registerViewModel=tempviewmodel
    }


}