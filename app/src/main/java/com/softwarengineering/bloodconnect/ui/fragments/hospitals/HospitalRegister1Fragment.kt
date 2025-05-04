package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentHospitalRegister1Binding
import com.softwarengineering.bloodconnect.viewmodel.HospitalregisterViewModel
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalRegister1Fragment : Fragment() {
    private lateinit var binding:FragmentHospitalRegister1Binding
    private lateinit var registerViewModel: HospitalregisterViewModel
    private lateinit var loginViewModel: LoginViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_hospital_register1,container,false)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)

        binding.buttonSubmit.setOnClickListener {
            val hospitalName = binding.hospitalname.text.toString().trim()
            val authorizedName = binding.authname.text.toString().trim()
            val phone = binding.phone.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.confirmpassword.text.toString().trim()

            // Validasyonlar...
            if (hospitalName.isEmpty() || authorizedName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Please enter a valid email address.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // ViewModel’e verileri set et
            registerViewModel.hospitalname = hospitalName
            registerViewModel.authname = authorizedName
            registerViewModel.phone = phone
            registerViewModel.mail = email
            registerViewModel.password = password

            // ✅ Artık kayıt sonrası login burada tetiklenecek
            registerViewModel.registerhospital(
                onSuccess = {
                    loginViewModel.loginhospital(
                        email,
                        password,
                        onSuccess = {
                            findNavController().navigate(R.id.action_hospitalRegister1Fragment_to_hospitalHomeFragment)
                        },
                        onFailure = {
                            Toast.makeText(requireContext(), "Login failed. Please check your information.", Toast.LENGTH_LONG).show()
                        },
                        onUnapproved = {
                            Toast.makeText(requireContext(), "Your hospital account has not been approved yet.", Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_hospitalRegister1Fragment_to_welcomeFragment)
                        }
                    )
                },
                onFailure = {
                    Toast.makeText(requireContext(), "Hospital registration failed: ${it.message}", Toast.LENGTH_LONG).show()
                }
            )
        }
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
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