package com.lkw1120.memo.datasource.entity

import androidx.room.*
import java.io.Serializable
import java.util.*

@Entity(tableName = "image",
    foreignKeys = [ForeignKey(
        entity = Memo::class,
        parentColumns = ["memo_id"],
        childColumns = ["memo_id"],
        onDelete = ForeignKey.CASCADE)])
data class Image (
    @PrimaryKey
    @ColumnInfo(name = "image_id")
    override var id: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = "memo_id", index = true)
    var mid: Long = 0,
    @ColumnInfo(name = "image_order")
    var order: Int = 0,
    @ColumnInfo(name = "image_src")
    var src: String? = null
): Serializable, BaseEntity