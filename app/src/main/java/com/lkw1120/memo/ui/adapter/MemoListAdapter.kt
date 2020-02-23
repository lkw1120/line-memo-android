package com.lkw1120.memo.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lkw1120.memo.databinding.ItemMemoBinding
import com.lkw1120.memo.datasource.entity.Memo
import com.lkw1120.memo.ui.DetailActivity

class MemoListAdapter: ListAdapter<Memo, RecyclerView.ViewHolder>(DiffCallback<Memo>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ItemViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val memo = getItem(position)
        (holder as ItemViewHolder).bind(memo)
    }

    inner class ItemViewHolder(private val binding: ItemMemoBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("id",binding.item!!.id)
                it.context.startActivity(intent)
            }
        }
        fun bind(item: Memo) {
            binding.apply {
                this.item = item
                executePendingBindings()
            }
        }
    }
}

