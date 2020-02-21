package com.lkw1120.memo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lkw1120.memo.datasource.entity.Memo
import com.lkw1120.memo.repository.MemoRepository

class MainViewModel(
    private val memoRepository: MemoRepository
) : ViewModel() {

    val memoList: LiveData<List<Memo>> = memoRepository.getMemoAll()
}