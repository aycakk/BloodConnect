package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentRegister1Binding


class Register1Fragment : Fragment() {
private lateinit var binding: FragmentRegister1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_register1, container, false)

        binding.buttonContinue.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_register1Fragment_to_register2Fragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}