package com.softwarengineering.bloodconnect.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softwarengineering.bloodconnect.data.model.NotificationItem
import com.softwarengineering.bloodconnect.databinding.ItemNotificationBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationAdapter(
    private val notificationsList: List<NotificationItem>
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = notificationsList[position]
        holder.binding.apply {
            textHospital.text = "${item.hospitalName} announced that it needs ${item.bloodType} blood."
            textDate.text = formatTimestamp(item.timestamp)
            //textUrgent.visibility = if (item.isUrgent) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount(): Int = notificationsList.size

    // Timestamp'ı tarih-saat string'e çeviriyoruz
    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }}

