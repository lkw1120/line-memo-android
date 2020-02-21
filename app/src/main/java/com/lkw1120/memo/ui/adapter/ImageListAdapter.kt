package com.lkw1120.memo.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lkw1120.memo.databinding.ItemImageBinding
import com.lkw1120.memo.datasource.entity.Image
import com.lkw1120.memo.ui.WriteActivity

class ImageListAdapter(private val context: Context): ListAdapter<Image, RecyclerView.ViewHolder>(DiffCallback<Image>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ItemViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val image = getItem(position)
        (holder as ItemViewHolder).bind(image)
    }

    inner class ItemViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

            }
            binding.itemDelete.setOnClickListener {
                val pos = adapterPosition
                if(pos != RecyclerView.NO_POSITION) {
                    if(listener != null) {
                        listener!!.onIconClick(it,pos)
                    }
                }
            }
        }
        fun bind(item: Image) {
            binding.run{
                this.item = item
                if((context as Activity) is WriteActivity)
                    this.itemDelete.visibility = View.VISIBLE
                else
                    this.itemDelete.visibility = View.GONE
                executePendingBindings()
            }
        }
    }

    interface OnIconClickListener {
        fun onIconClick(view: View, pos: Int)
    }

    private var listener: OnIconClickListener? = null

    fun setOnIconClickListener(listener: OnIconClickListener) {
        this.listener = listener
    }
}