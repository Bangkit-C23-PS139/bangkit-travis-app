package com.rickyslash.travis.ui.settings.preference

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.travis.databinding.ItemTravelPreferenceBinding
import com.rickyslash.travis.helper.convertSnakeCaseToSentence
import com.rickyslash.travis.helper.getRandomMaterialColor

class TravelPreferenceAdapter(
    private val selfPreferenceList: List<String>,
    private val travelPreferenceList: List<String>
) : RecyclerView.Adapter<TravelPreferenceAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemTravelPreferenceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val radioButton: RadioButton = binding.rbItemTpref
    }

    private val selectedPreference: MutableList<String> = mutableListOf<String>().apply {
        addAll(selfPreferenceList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTravelPreferenceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = travelPreferenceList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = travelPreferenceList[position]
        val isDataSelected = selfPreferenceList.contains(data)
        val isSelected = selectedPreference.contains(data)

        holder.radioButton.isChecked = isSelected
        holder.radioButton.text = convertSnakeCaseToSentence(data)
        Glide.with(holder.itemView.context)
            .load("https://source.unsplash.com/512x512/?${convertSnakeCaseToSentence(data)}")
            .placeholder(ColorDrawable(getRandomMaterialColor()))
            .into(holder.binding.ivItemTpref)

        holder.radioButton.setOnClickListener {
            if (isSelected) {
                selectedPreference.remove(data)
            } else {
                selectedPreference.add(data)
            }
            notifyItemChanged(position)
        }
    }

    fun getCheckedPreference(): MutableList<String> {
        return selectedPreference
    }
}
