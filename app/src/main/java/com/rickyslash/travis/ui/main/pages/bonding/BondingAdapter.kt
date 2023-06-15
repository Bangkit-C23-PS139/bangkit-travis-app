package com.rickyslash.travis.ui.main.pages.bonding

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.travis.api.response.BondingDataItem
import com.rickyslash.travis.api.response.BondingListDataItem
import com.rickyslash.travis.databinding.ItemBondingBinding
import com.rickyslash.travis.helper.getStartDateFromISODate
import com.rickyslash.travis.helper.getRandomMaterialColor

class BondingAdapter: PagingDataAdapter<BondingDataItem, BondingAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemCallback: OnItemClickCallback
    private lateinit var onButtonJoinClickCallback: OnButtonJoinClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemCallback = onItemClickCallback
    }

    fun setOnButtonJoinClickCallback(onButtonJoinClickCallback: OnButtonJoinClickCallback) {
        this.onButtonJoinClickCallback = onButtonJoinClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBondingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.gmapLink))
                holder.itemView.context.startActivity(navIntent)
            }
        }
    }

    inner class ViewHolder(var binding: ItemBondingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BondingDataItem) {
            binding.tvItemBondingTitle.text = data.activityName
            binding.tvItemBondingDate.text = getStartDateFromISODate(data.createdAt)
            binding.ivThumb1.setImageDrawable(ColorDrawable(getRandomMaterialColor()))
            binding.ivThumb2.setImageDrawable(ColorDrawable(getRandomMaterialColor()))
            binding.ivThumb3.setImageDrawable(ColorDrawable(getRandomMaterialColor()))
            Glide.with(itemView.context)
                .load(data.backgroundImg)
                .placeholder(ColorDrawable(getRandomMaterialColor()))
                .into(binding.ivItemBondingImg)

            binding.btnItemBondingMore.setOnClickListener {
                val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.gmapLink))
                itemView.context.startActivity(navIntent)
            }

            binding.btnBondingNavigate.setOnClickListener {
                val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.gmapLink))
                itemView.context.startActivity(navIntent)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BondingListDataItem)
    }

    interface OnButtonJoinClickCallback {
        fun onButtonJoinClicked(bondingId: String)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BondingDataItem>() {
            override fun areItemsTheSame(oldItem: BondingDataItem, newItem: BondingDataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BondingDataItem, newItem: BondingDataItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}