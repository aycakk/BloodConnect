package com.softwarengineering.bloodconnect.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.data.model.Match
import com.softwarengineering.bloodconnect.databinding.MatchdonorcardBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.databinding.DataBindingUtil

class MatchDonorAdapter(val context:Context , val list:List<Match>):RecyclerView.Adapter<MatchDonorAdapter.Matchcardholder>() {
    inner class Matchcardholder(val binding: MatchdonorcardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Matchcardholder {
        val binding:MatchdonorcardBinding=inflate(LayoutInflater.from(context), R.layout.matchdonorcard,parent,false)
        return  Matchcardholder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Matchcardholder, position: Int) {

        val match = list[position]
        holder.binding.nametext.text = match.donorName
        holder.binding.scoretext.text = match.matchScore.toString()
        holder.binding.bloodgrouptext.text = match.donorBloodType

    }
}