package com.rickyslash.travis.ui.profile

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickyslash.travis.api.entity.BondingData
import com.rickyslash.travis.databinding.ItemProfileBinding
import com.rickyslash.travis.helper.getStartDateFromISODate

class ProfileAdapter(private val selfUserBondingData: List<BondingData>): RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemProfileBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = selfUserBondingData[position]
        holder.binding.tvItemProfileTitle.text = data.bondingName
        holder.binding.tvItemProfileDate.text = "${getStartDateFromISODate(data.startTime)} - ${getStartDateFromISODate(data.endTime)}"
        holder.itemView.setOnClickListener {
            val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.gmapLink))
            holder.itemView.context.startActivity(navIntent)
        }
    }

    override fun getItemCount(): Int = selfUserBondingData.size

}