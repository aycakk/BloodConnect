package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_welcome,container,false)
        binding.welcomefragment=this
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        binding.buttonCreateaccount.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_welcomeFragment_to_register1Fragment)
        }
        binding.buttonLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
        binding.buttonLoginhospital.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_welcomeFragment_to_hospitalLoginFragment)
        }
        binding.buttonForm.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_welcomeFragment_to_hospitalRegister1Fragment)
        }

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        return binding.root
    }


}