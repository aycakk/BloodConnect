package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.databinding.FragmentWiewRequestBloodBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WiewRequestBloodFragment : Fragment() {
    private lateinit var binding:FragmentWiewRequestBloodBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // binding=DataBindingUtil.inflate(inflater,R.layout.fragment_wiew_request_blood, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


}