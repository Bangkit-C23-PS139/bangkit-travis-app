package com.rickyslash.travis.ui.travelplan

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.rickyslash.travis.R
import com.rickyslash.travis.api.dummy.dummyresponse.TravelPlanItem
import com.rickyslash.travis.api.response.DestinationRecommendationItem
import com.rickyslash.travis.databinding.ItemTravelPlanBinding
import com.rickyslash.travis.helper.extractDestinationKeyword
import com.rickyslash.travis.helper.getRandomMaterialColor

class TravelPlanAdapter(private val travelPlanList: List<DestinationRecommendationItem>): RecyclerView.Adapter<TravelPlanAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemTravelPlanBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTravelPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = travelPlanList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = travelPlanList[position]
        holder.binding.tvItemTplanLabel.text = extractDestinationKeyword(data.keywordCategory)
        holder.binding.tvItemTplanTitle.text = data.name
        holder.binding.tvItemTplanDesc.text = data.address
        Glide.with(holder.itemView.context)
            .load(data.imgUrl)
            .placeholder(ColorDrawable(getRandomMaterialColor()))
            .into(holder.binding.ivItemTplan)

        holder.binding.btnItemTplanEdit.setOnClickListener { dialogPromptEdit(holder.itemView.context, data, (holder.binding)) }

        holder.binding.btnItemTplanNavigate.setOnClickListener {
            val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.mapUrl))
            holder.itemView.context.startActivity(navIntent)
        }

        holder.itemView.setOnClickListener {
            val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.mapUrl))
            holder.itemView.context.startActivity(navIntent)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TravelPlanItem)
    }

    private fun dialogPromptEdit(context: Context, data: DestinationRecommendationItem, binding: ItemTravelPlanBinding) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.view_dialog_tplan_item_edit, null)
        val dialogTitle = dialogView.findViewById<TextInputEditText>(R.id.edtx_dialog_tplan_title)
        val dialogDesc = dialogView.findViewById<TextInputEditText>(R.id.edtx_dialog_tplan_desc)
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.label_item_tplan_edit))
            setView(dialogView)
            setPositiveButton(context.getString(R.string.label_ok)) { _, _ ->
                when {
                    dialogTitle.text.isNullOrBlank() -> {
                        Toast.makeText(context, R.string.label_tplan_empty_destination, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        if (!dialogDesc.text.isNullOrBlank()) {
                            binding.tvItemTplanDesc.text = dialogDesc.text.toString()
                            data.address = dialogDesc.text.toString()
                        }
                        binding.tvItemTplanTitle.text = dialogTitle.text.toString()
                        data.name = dialogTitle.text.toString()
                        binding.ivItemTplan.setImageResource(R.drawable.thumb_tplan_custom)
                        binding.tvItemTplanLabel.text = context.getString(R.string.label_user_input)
                        data.keywordCategory = "['User Input']"
                        data.imgUrl = "https://source.unsplash.com/512x512/?${dialogTitle.text.toString()}"
                    }
                }
            }
            setNegativeButton(context.getString(R.string.label_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }
}