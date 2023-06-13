package com.rickyslash.travis.ui.main.pages.service

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.travis.api.dummy.dummyresponse.ServiceItem
import com.rickyslash.travis.databinding.ItemServiceBinding
import com.rickyslash.travis.helper.formatPriceToK
import com.rickyslash.travis.helper.getRandomMaterialColor

class ServiceAdapter(private val serviceList: List<ServiceItem>): RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemServiceBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = serviceList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = serviceList[position]
        holder.binding.tvItemServiceTitle.text = data.description
        holder.binding.tvItemServiceProvider.text = data.name
        holder.binding.tvItemServicePrice.text = formatPriceToK(data.createdAt.substring(0,4).toInt())
        Glide.with(holder.itemView.context)
            .load(data.photoUrl)
            .placeholder(ColorDrawable(getRandomMaterialColor()))
            .into(holder.binding.ivItemService)

        holder.itemView.setOnClickListener { onItemCallback.onItemClicked(serviceList[position]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ServiceItem)
    }

}