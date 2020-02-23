package com.lkw1120.memo.datasource.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "memo")
data class Memo(
    @PrimaryKey
    @ColumnInfo(name = "memo_id")
    override var id: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = "memo_title")
    var title: String = "",
    @ColumnInfo(name = "memo_message")
    var message: String = "",
    @ColumnInfo(name = "memo_thumbnail")
    var thumbnail: String? = null
): Serializable, BaseEntity