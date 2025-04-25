package com.softwarengineering.bloodconnect.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.softwarengineering.bloodconnect.data.model.NotificationItem
import com.softwarengineering.bloodconnect.databinding.ItemNotificationBinding

class NotificationAdapter(
    private val items: List<NotificationItem>
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            textHospital.text = "${item.hospitalName} announced that it needs ${item.bloodType} blood."
            textDate.text = item.dateTime
            textUrgent.visibility = if (item.isUrgent) View.VISIBLE else View.GONE

            buttonDetails.setOnClickListener {
                Toast.makeText(root.context, "Details clicked for ${item.hospitalName}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = items.size
}

