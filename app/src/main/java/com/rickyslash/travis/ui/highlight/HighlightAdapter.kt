package com.rickyslash.travis.ui.highlight

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.travis.R
import com.rickyslash.travis.api.response.HighlightDataItem
import com.rickyslash.travis.databinding.ItemHighlightBinding
import com.rickyslash.travis.helper.getRandomMaterialColor

class HighlightAdapter: PagingDataAdapter<HighlightDataItem, HighlightAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHighlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                // onItemClickCallback.onItemClicked(data)
                val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.gmapLink))
                holder.itemView.context.startActivity(navIntent)
            }
        }
    }

    inner class ViewHolder(private var binding: ItemHighlightBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HighlightDataItem) {
            binding.tvItemHighlightTitle.text = data.destinationName
            binding.tvItemHighlightDesc.text = data.description
            binding.tvTags.text = data.activity[0]
            binding.tvTagsAmount.text = itemView.context.getString(R.string.arg_number_overflow, (data.activity.size-1))
            glideImage(itemView.context, data.backgroundImg, binding.ivItemHighlightImg)
            glideImage(itemView.context, data.imageGallery[0], binding.ivThumb1)
            glideImage(itemView.context, data.imageGallery[1], binding.ivThumb2)
            glideImage(itemView.context, data.imageGallery[2], binding.ivThumb3)

            binding.btnHighlightNavigate.setOnClickListener {
                val navIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.gmapLink))
                itemView.context.startActivity(navIntent)
            }
        }
    }

    private fun glideImage(context: Context, imgData: String, ivData: ImageView) {
        Glide.with(context)
            .load(imgData)
            .placeholder(ColorDrawable(getRandomMaterialColor()))
            .into(ivData)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: HighlightDataItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HighlightDataItem>() {
            override fun areItemsTheSame(oldItem: HighlightDataItem, newItem: HighlightDataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HighlightDataItem, newItem: HighlightDataItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}