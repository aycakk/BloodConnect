package com.softwarengineering.bloodconnect.ui.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentProfileBinding
import com.softwarengineering.bloodconnect.viewmodel.DonorViewModel
import com.softwarengineering.bloodconnect.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ProfileFragment : Fragment() {
private lateinit var binding:FragmentProfileBinding
private lateinit var viewModel: DonorViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        binding.profiefragment=this
        binding.lifecycleOwner=viewLifecycleOwner
        viewModel.loadCurrentDonor()
        binding.viewmodel=viewModel


        binding.settingsButton.setOnClickListener {

            binding.editprofile.visibility=View.VISIBLE
        }
        binding.viewprofile.setOnClickListener {
            binding.editprofile.visibility=View.GONE
        }
        viewModel.donorData.observe(viewLifecycleOwner){
            binding.imageprofile.setImageResource(
                if (it.gender=="male")
                R.drawable.man
                else
                R.drawable.human
            )
            binding.fullname.text="${it.name} " +"${it.surname}"
            binding.mail.text="${it.email}"
            binding.gendertext.text="Gender: ${it.gender}"

            binding.phonetext.text="Phone: ${it.phone}"
            binding.adresstext.text="Adress: ${it.address}"
            val timestamp = it.birthDate
            if (timestamp != null) {
                val date = timestamp.toDate()
                val format = SimpleDateFormat("dd/MM/yyyy ", Locale.getDefault())
                val formattedDate = format.format(date)
                Log.d("Tarih", "Oluşturulma zamanı: $formattedDate")
                binding.birthdatetext.text="Birth Date: ${formattedDate}"
            }


        }


        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: DonorViewModel by viewModels()
        viewModel=tempviewmodel
    }


}