package com.lkw1120.memo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lkw1120.memo.datasource.entity.MemoWithImages
import com.lkw1120.memo.repository.MemoRepository

class WriteViewModelFactory(
    private val memoRepository: MemoRepository,
    private val memoWithImages: MemoWithImages
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WriteViewModel(memoRepository,memoWithImages) as T
    }
}