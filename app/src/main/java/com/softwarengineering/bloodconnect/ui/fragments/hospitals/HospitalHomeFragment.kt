package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentHospitalHomeBinding
import com.softwarengineering.bloodconnect.databinding.FragmentHospitalLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalHomeFragment : Fragment() {
private lateinit var bindig: FragmentHospitalHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindig=DataBindingUtil.inflate(inflater,R.layout.fragment_hospital_home, container, false)
        // Inflate the layout for this fragment
        bindig.imageButtonListdonor.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_hospitalHomeFragment_to_listmatchDonorFragment)
        }
        return bindig.root
    }


}