package com.rickyslash.travis.ui.main.pages.bonding

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.travis.api.response.BondingItem
import com.rickyslash.travis.databinding.ItemBondingBinding
import com.rickyslash.travis.helper.getExactStartDateFromISODate
import com.rickyslash.travis.helper.getRandomMaterialColor

class BondingAdapter(private val bondingList: List<BondingItem>): RecyclerView.Adapter<BondingAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemBondingBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBondingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = bondingList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = bondingList[position]
        holder.binding.tvItemBondingTitle.text = data.name
        holder.binding.tvItemBondingDate.text = getExactStartDateFromISODate(data.createdAt)
        holder.binding.ivThumb1.setImageDrawable(ColorDrawable(getRandomMaterialColor()))
        holder.binding.ivThumb2.setImageDrawable(ColorDrawable(getRandomMaterialColor()))
        holder.binding.ivThumb3.setImageDrawable(ColorDrawable(getRandomMaterialColor()))
        Glide.with(holder.itemView.context)
            .load(data.photoUrl)
            .placeholder(ColorDrawable(getRandomMaterialColor()))
            .into(holder.binding.ivItemBondingImg)

        holder.binding.btnBondingNavigate.setOnClickListener {
            val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.photoUrl))
            holder.itemView.context.startActivity(navIntent)
        }

        holder.binding.btnItemBondingMore.setOnClickListener{ onItemCallback.onItemClicked(bondingList[position]) }
        holder.itemView.setOnClickListener { onItemCallback.onItemClicked(bondingList[position]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BondingItem)
    }

}