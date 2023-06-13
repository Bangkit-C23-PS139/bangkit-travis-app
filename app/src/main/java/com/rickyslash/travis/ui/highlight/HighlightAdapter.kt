package com.rickyslash.travis.ui.highlight

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.travis.api.dummy.dummyresponse.HighlightItem
import com.rickyslash.travis.databinding.ItemHighlightBinding
import com.rickyslash.travis.helper.getRandomMaterialColor

class HighlightAdapter(private val highlightList: List<HighlightItem>): RecyclerView.Adapter<HighlightAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemHighlightBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHighlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = highlightList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = highlightList[position]
        holder.binding.tvItemHighlightTitle.text = data.name
        holder.binding.tvItemHighlightDesc.text = data.description
        holder.binding.tvTags.text = getRandomMaterialColor().toString().substring(1)
        holder.binding.tvTagsAmount.text = if (position.toString().length > 2) { position.toString().substring(0,2) } else { position.toString() }
        holder.binding.ivThumb1.setImageDrawable(ColorDrawable(getRandomMaterialColor()))
        holder.binding.ivThumb2.setImageDrawable(ColorDrawable(getRandomMaterialColor()))
        holder.binding.ivThumb3.setImageDrawable(ColorDrawable(getRandomMaterialColor()))
        Glide.with(holder.itemView.context)
            .load(data.photoUrl)
            .placeholder(ColorDrawable(getRandomMaterialColor()))
            .into(holder.binding.ivItemHighlightImg)

        holder.binding.btnHighlightNavigate.setOnClickListener {
            val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.photoUrl))
            holder.itemView.context.startActivity(navIntent)
        }

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(highlightList[position]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: HighlightItem)
    }

}