package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
private lateinit var loginViewModel:LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_register1, container, false)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.red)

        binding.buttonContinue.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val lastname = binding.editTextLastname.text.toString().trim()
            val idnumber = binding.idnumber.text.toString().trim()
            val phonenumber = binding.phonenumber.text.toString().trim()
            val birthdate = binding.birthdate.text.toString().trim()
            val adress = binding.editTextadress.text.toString().trim()
            val gender = binding.spinnergende.selectedItem.toString()
            val blood = binding.spinnerbloodtype.selectedItem.toString()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmpassword = binding.confirmpassword.text.toString().trim()

            // Boş alan kontrolü
            if (name.isEmpty() || lastname.isEmpty() || idnumber.isEmpty() || phonenumber.isEmpty()
                || birthdate.isEmpty() || adress.isEmpty() || gender.isEmpty() || blood.isEmpty()
                || email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()
            ) {
                Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Şifre eşleşmesi kontrolü
            if (password != confirmpassword) {
                Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Eğer her şey doğruysa verileri ViewModel'e aktar
            viewModel.name = name
            viewModel.lastname = lastname
            viewModel.idnumber = idnumber
            viewModel.phonenumber = phonenumber
            viewModel.birthdate = birthdate
            viewModel.adress = adress
            viewModel.gender = gender
            viewModel.blood = blood
            viewModel.email = email
            viewModel.password = password

            // Kaydı başlat
            viewModel.registerDonor()

            // Kayıt başarılıysa login yap ve home'a git
            loginViewModel.loginUser(viewModel.email, viewModel.password,
                onSuccess = {
                    Navigation.findNavController(it).navigate(R.id.action_register1Fragment_to_homeFragment)
                },
                onFailure = {
                    Toast.makeText(context, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }



        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: RegisterViewModel by viewModels()
        viewModel=tempviewmodel
        val temviewmodel:LoginViewModel by viewModels()
        loginViewModel=temviewmodel
    }


}