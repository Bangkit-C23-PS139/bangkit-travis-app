package com.rickyslash.travis.ui.settings.preference

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.travis.databinding.ItemTravelPreferenceBinding
import com.rickyslash.travis.helper.convertSnakeCaseToSentence
import com.rickyslash.travis.helper.getRandomMaterialColor

class TravelPreferenceAdapter(private val selfPreferenceList: List<String>, private val travelPreferenceList: List<String>): RecyclerView.Adapter<TravelPreferenceAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemTravelPreferenceBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTravelPreferenceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = travelPreferenceList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = travelPreferenceList[position]
        val isDataSelected = selfPreferenceList.contains(data)

        if (isDataSelected && !holder.binding.rbItemTpref.isSelected) {
            holder.binding.rbItemTpref.isChecked = true
            holder.binding.rbItemTpref.isSelected = true
        } else if (!isDataSelected && holder.binding.rbItemTpref.isSelected) {
            holder.binding.rbItemTpref.isChecked = false
            holder.binding.rbItemTpref.isSelected = false
        }

        val dataSentence = convertSnakeCaseToSentence(data)
        holder.binding.rbItemTpref.text = dataSentence
        Glide.with(holder.itemView.context)
            .load("https://source.unsplash.com/512x512/?$dataSentence")
            .placeholder(ColorDrawable(getRandomMaterialColor()))
            .into(holder.binding.ivItemTpref)

        holder.binding.rbItemTpref.setOnClickListener {
            if (!holder.binding.rbItemTpref.isSelected) {
                holder.binding.rbItemTpref.isChecked = true
                holder.binding.rbItemTpref.isSelected = true
            } else {
                holder.binding.rbItemTpref.isChecked = false
                holder.binding.rbItemTpref.isSelected = false
            }
        }
    }

}