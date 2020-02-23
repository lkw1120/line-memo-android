package com.lkw1120.memo.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.lkw1120.memo.datasource.entity.BaseEntity

class DiffCallback<T: BaseEntity>: DiffUtil.ItemCallback<T>() {

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.id == newItem.id

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem
}