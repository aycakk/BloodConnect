package com.softwarengineering.bloodconnect.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softwarengineering.bloodconnect.data.model.DonationTrackingModel
import com.softwarengineering.bloodconnect.databinding.ItemDonationTrackingBinding

class DonationTrackingAdapter(private val donationList: List<DonationTrackingModel>,
                              private val onDetailsClick: (DonationTrackingModel) -> Unit) :
    RecyclerView.Adapter<DonationTrackingAdapter.DonationViewHolder>() {

    inner class DonationViewHolder(val binding: ItemDonationTrackingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationViewHolder {
        val binding = ItemDonationTrackingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DonationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonationViewHolder, position: Int) {
        val donation = donationList[position]
        holder.binding.apply {
            donorNameTextView.text = donation.donorName
            hospitalNameTextView.text = donation.hospitalName
            donationDateTextView.text = donation.donationDate
            detailsButton.setOnClickListener {
                onDetailsClick.invoke(donation)
            }
        }
    }

    override fun getItemCount(): Int = donationList.size
}
