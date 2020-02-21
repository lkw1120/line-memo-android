package com.lkw1120.memo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lkw1120.memo.repository.MemoRepository

class MainViewModelFactory(
    private val memoRepository: MemoRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(memoRepository) as T
    }
}