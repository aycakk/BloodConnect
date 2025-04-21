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


class MatchDonorAdapter(var mcontext:Context , var list:List<Match>):RecyclerView.Adapter<MatchDonorAdapter.matchcardholder>() {
    inner class matchcardholder(var design: MatchdonorcardBinding):RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): matchcardholder {
        val binding:MatchdonorcardBinding=inflate(LayoutInflater.from(mcontext), R.layout.matchdonorcard,parent,false)
        return  matchcardholder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: matchcardholder, position: Int) {

        val match=list.get(position)
        val m=holder.design
        m.match=match
    }
}