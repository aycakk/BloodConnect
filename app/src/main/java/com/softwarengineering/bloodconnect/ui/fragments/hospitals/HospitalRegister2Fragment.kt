package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentHospitalRegister2Binding
import com.softwarengineering.bloodconnect.viewmodel.HospitalregisterViewModel
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalRegister2Fragment : Fragment() {
private lateinit var binding:FragmentHospitalRegister2Binding
private lateinit var registerViewModel: HospitalregisterViewModel
private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_hospital_register2, container, false)
        binding.buttonContinue.setOnClickListener {
            registerViewModel.authname=binding.authname.text.toString()
            registerViewModel.phone=binding.phone.text.toString()
            registerViewModel.mail=binding.email.text.toString()
            registerViewModel.password=binding.password.text.toString()
            registerViewModel.registerhospital()
            loginViewModel.loginhospital(registerViewModel.mail,registerViewModel.password,
                onSuccess = { Navigation.findNavController(it).navigate(R.id.action_hospitalRegister2Fragment_to_hospitalHomeFragment2)}
                ,
                onFailure = {
                    Toast.makeText(requireContext(), "login failure", Toast.LENGTH_LONG).show()
                },
                onUnapproved = {
                    Toast.makeText(requireContext(), "Your hospital account has not been approved yet.", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(it).navigate(R.id.action_hospitalRegister2Fragment_to_welcomeFragment)
                }
            )
        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hostempviewmodel: HospitalregisterViewModel by viewModels()
         val logintempViewModel:LoginViewModel by viewModels()
        registerViewModel=hostempviewmodel
        loginViewModel=logintempViewModel
    }


}