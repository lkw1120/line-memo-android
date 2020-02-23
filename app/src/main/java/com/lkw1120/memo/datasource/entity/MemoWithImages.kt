package com.lkw1120.memo.datasource.entity

import java.io.Serializable

data class MemoWithImages(
    val memo: Memo = Memo(),
    val imageList: List<Image> = mutableListOf()
): Serializable