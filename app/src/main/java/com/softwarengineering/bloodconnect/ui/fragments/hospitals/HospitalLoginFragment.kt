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
import com.softwarengineering.bloodconnect.databinding.FragmentHospitalLoginBinding
import com.softwarengineering.bloodconnect.viewmodel.HospitalregisterViewModel
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalLoginFragment : Fragment() {
    private lateinit var binding:FragmentHospitalLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_hospital_login, container, false)
        binding.buttonLogin.setOnClickListener {
            viewModel.loginhospital(binding.edittextemail.text.toString(),binding.editTextpassword.text.toString(),
                onSuccess = { Navigation.findNavController(it).navigate(R.id.action_hospitalLoginFragment_to_hospitalHomeFragment) },
                onFailure = { Toast.makeText(context, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()},
            onUnapproved = {
                Toast.makeText(requireContext(), "Your hospital account has not been approved yet.", Toast.LENGTH_LONG).show()

            })

        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: LoginViewModel by viewModels()
        viewModel=tempviewmodel
    }


}