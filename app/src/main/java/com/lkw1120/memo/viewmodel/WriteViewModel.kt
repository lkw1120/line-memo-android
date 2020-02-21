package com.lkw1120.memo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkw1120.memo.datasource.entity.Image
import com.lkw1120.memo.datasource.entity.Memo
import com.lkw1120.memo.datasource.entity.MemoWithImages
import com.lkw1120.memo.repository.MemoRepository
import kotlinx.coroutines.launch

class WriteViewModel(
    private val memoRepository: MemoRepository,
    private val memoWithImages: MemoWithImages
) : ViewModel() {

    val memo: LiveData<Memo> = MutableLiveData(memoWithImages.memo)
    val imageList: LiveData<List<Image>> = MutableLiveData(memoWithImages.imageList)

    fun addImage(item: Image) {
        val newList:MutableList<Image> = mutableListOf()
        newList.addAll(imageList.value!!)
        (imageList as MutableLiveData<List<Image>>)
            .postValue(newList.apply { add(item) })
    }

    fun removeImage(item: Image) {
        val newList:MutableList<Image> = mutableListOf()
        newList.addAll(imageList.value!!)
        (imageList as MutableLiveData<List<Image>>)
            .postValue(newList.apply { remove(item) })
    }

    fun updateMemoWithImages() = viewModelScope.launch {
        val memo = memo.value!!
        val imageList = imageList.value!!
        memo.apply {
            thumbnail = null
            if(imageList.isNotEmpty()) {
                thumbnail = imageList[0].src
                for(item in imageList) {
                    item.mid = id
                    item.order = imageList.indexOf(item)
                }
            }
        }
        memoRepository.updateMemoWithImages(memo, imageList)
    }
}
