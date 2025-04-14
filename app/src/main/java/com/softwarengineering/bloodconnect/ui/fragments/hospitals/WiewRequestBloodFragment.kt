package com.softwarengineering.bloodconnect.ui.fragments.hospitals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softwarengineering.bloodconnect.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WiewRequestBloodFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wiew_request_blood, container, false)
    }


}