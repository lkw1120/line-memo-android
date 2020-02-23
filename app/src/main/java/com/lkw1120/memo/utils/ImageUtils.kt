package com.lkw1120.memo.utils

import android.content.Context
import android.webkit.MimeTypeMap
import com.lkw1120.memo.R


object ImageUtils {
    fun isImage(context: Context, imageSrc: String): Boolean {
        val extension = MimeTypeMap.getFileExtensionFromUrl(imageSrc)
        val whiteList = context.resources.getStringArray(R.array.image_filter)
        return whiteList.contains(extension)
    }
}