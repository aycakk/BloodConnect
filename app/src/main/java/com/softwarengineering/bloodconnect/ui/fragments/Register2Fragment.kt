package com.softwarengineering.bloodconnect.ui.fragments

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
import com.softwarengineering.bloodconnect.databinding.FragmentRegister2Binding
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import com.softwarengineering.bloodconnect.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class Register2Fragment : Fragment() {
private lateinit var binding:FragmentRegister2Binding
private lateinit var registerviewModel: RegisterViewModel
private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_register2, container, false)

        binding.buttonContinue.setOnClickListener {
            registerviewModel.gender=binding.spinnergende.toString()
            registerviewModel.blood=binding.spinnerbloodtype.toString()
            registerviewModel .email=binding.email.text.toString()
            registerviewModel. password=binding.password.text.toString()
            val confirmpassword=binding.confirmpassword.text.toString()
            registerviewModel.registerDonor()
            loginViewModel.loginUser(registerviewModel.email,registerviewModel.password,
                onSuccess = { Navigation.findNavController(it).navigate(R.id.action_register2Fragment_to_homeFragment)}
              ,
                onFailure = {Toast.makeText(context, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()}
                )

        }

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel:LoginViewModel by viewModels()
        loginViewModel=tempviewmodel
        val registertemp:RegisterViewModel by viewModels()
        registerviewModel=registertemp
    }


}