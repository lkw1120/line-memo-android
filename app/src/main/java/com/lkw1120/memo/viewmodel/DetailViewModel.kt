package com.lkw1120.memo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkw1120.memo.datasource.entity.Image
import com.lkw1120.memo.datasource.entity.Memo
import com.lkw1120.memo.repository.MemoRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val memoRepository: MemoRepository,
    private val id:Long
): ViewModel() {

    val memo:LiveData<Memo> = memoRepository.getMemoById(id)
    val imageList:LiveData<List<Image>> = memoRepository.getImagesByMid(id)

    fun deleteMemo() = viewModelScope.launch {
        memoRepository.deleteMemo(memo.value!!)
    }

}