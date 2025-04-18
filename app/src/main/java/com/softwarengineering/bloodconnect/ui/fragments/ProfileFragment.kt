package com.softwarengineering.bloodconnect.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        binding.settingsButton.setOnClickListener {

            binding.editprofile.visibility=View.VISIBLE
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}