package com.softwarengineering.bloodconnect.ui.adapter

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate

import androidx.recyclerview.widget.RecyclerView
import com.softwarengineering.bloodconnect.R
import com.softwarengineering.bloodconnect.data.model.Request

import com.softwarengineering.bloodconnect.databinding.RequestviewcardBinding
import com.softwarengineering.bloodconnect.viewmodel.HospitalviewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


class ViewRequestAdapter(var mcontext:Context,var list:List<Request>,var viewmodel:HospitalviewModel):RecyclerView.Adapter<ViewRequestAdapter.requestviewcardholder>(){

    inner class requestviewcardholder(var design:RequestviewcardBinding):RecyclerView.ViewHolder(design.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewRequestAdapter.requestviewcardholder {
        val  binding:RequestviewcardBinding=
            inflate(LayoutInflater.from(mcontext), R.layout.requestviewcard,parent,false)
        return requestviewcardholder(binding)

    }

    override fun onBindViewHolder(holder: ViewRequestAdapter.requestviewcardholder, position: Int) {
        val request=list.get(position)
        val r=holder.design
        r.request=request
        val timestamp = request.requiredDate
        if (timestamp != null) {
            val date = timestamp.toDate()
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val formattedDate = format.format(date)
            Log.d("Tarih", "Oluşturulma zamanı: $formattedDate")
            r.date.text=formattedDate
        }


        r.units.text=request.units.toString()
        


    }


    override fun getItemCount(): Int {
        return list.size

    }


}