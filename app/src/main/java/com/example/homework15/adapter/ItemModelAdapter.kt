package com.example.homework15.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework15.R
import com.example.homework15.data.ItemModel
import com.example.homework15.data.LastMessageType
import com.example.homework15.databinding.RecyclerItemBinding
import com.example.homework15.extensions.setImage

class ItemModelAdapter :
    ListAdapter<ItemModel, ItemModelAdapter.ItemModelViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemModelViewHolder {
        val binding =
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemModelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemModelViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ItemModel) {
            binding.tvName.text = item.owner
            with(binding) {
                ivImage.setImage(item.image ?: "")
                tvLastMessage.text = when (item.lasteMessageType?.lowercase()) {
                    LastMessageType.FILE.name.lowercase() -> {
                        icMessageType.setImageResource(R.drawable.ic_file)
                        "Sent an attachment"
                    }

                    LastMessageType.TEXT.name.lowercase() -> {
                        icMessageType.visibility = View.GONE
                        item.lastMessage
                    }

                    LastMessageType.VOICE.name.lowercase() -> {
                        icMessageType.setImageResource(R.drawable.ic_speaker)
                        "Sent a voice message"
                    }

                    else -> {
                        icMessageType.setImageDrawable(null)
                        ""
                    }
                }

                tvTime.text = item.lastActive

                if (item.unreadMessages != null && item.unreadMessages > 0) {
                    tvUnreadMessages.visibility = View.VISIBLE
                    tvUnreadMessages.text = item.unreadMessages.toString()
                } else {
                    tvUnreadMessages.visibility = View.GONE
                }

                if (item.isTyping == true) {
                    ivTyping.visibility = View.VISIBLE
                    tvUnreadMessages.visibility = View.GONE
                } else {
                    ivTyping.visibility = View.GONE
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }
    }
}