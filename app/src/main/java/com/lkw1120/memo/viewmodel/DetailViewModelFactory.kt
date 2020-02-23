package com.lkw1120.memo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lkw1120.memo.repository.MemoRepository

class DetailViewModelFactory(
    private val memoRepository: MemoRepository,
    private val id: Long
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(memoRepository,id) as T
    }
}