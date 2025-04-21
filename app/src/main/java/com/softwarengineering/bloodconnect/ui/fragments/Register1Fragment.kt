package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentRegister1Binding
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import com.softwarengineering.bloodconnect.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.softwarengineering.bloodconnect.utils.toBase64
import com.softwarengineering.bloodconnect.utils.fromBase64


@AndroidEntryPoint
class Register1Fragment : Fragment() {
private lateinit var binding: FragmentRegister1Binding
private lateinit var viewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_register1, container, false)

        binding.buttonContinue.setOnClickListener {
            viewModel.name=binding.name.text.toString()
            viewModel.lastname=binding.editTextLastname.text.toString()
            viewModel.idnumber=binding.idnumber.text.toString().toBase64()
            viewModel.phonenumber=binding.phonenumber.text.toString()
            viewModel.adress=binding.editTextadress.text.toString()


            Navigation.findNavController(it).navigate(R.id.action_register1Fragment_to_register2Fragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: RegisterViewModel by viewModels()
        viewModel=tempviewmodel
    }


}