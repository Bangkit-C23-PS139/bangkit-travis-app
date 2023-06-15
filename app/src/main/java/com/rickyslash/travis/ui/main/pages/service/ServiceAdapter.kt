package com.rickyslash.travis.ui.main.pages.service

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.travis.api.dummy.dummyresponse.ServiceItem
import com.rickyslash.travis.api.response.ServiceDataItem
import com.rickyslash.travis.databinding.ItemServiceBinding
import com.rickyslash.travis.helper.formatPriceToK
import com.rickyslash.travis.helper.getRandomMaterialColor

class ServiceAdapter: PagingDataAdapter<ServiceDataItem, ServiceAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(var binding: ItemServiceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ServiceDataItem) {
            binding.tvItemServiceTitle.text = data.serviceName
            binding.tvItemServiceProvider.text = data.serviceProvider
            binding.tvItemServicePrice.text = formatPriceToK(data.servicePrice)
            Glide.with(itemView.context)
                .load(data.backgroundImg)
                .placeholder(ColorDrawable(getRandomMaterialColor()))
                .into(binding.ivItemService)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ServiceItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ServiceDataItem>() {
            override fun areItemsTheSame(oldItem: ServiceDataItem, newItem: ServiceDataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ServiceDataItem, newItem: ServiceDataItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}