package com.lkw1120.memo.repository

import android.util.Log
import com.lkw1120.memo.datasource.entity.Image
import com.lkw1120.memo.datasource.entity.Memo
import com.lkw1120.memo.datasource.local.dao.MemoDao

class MemoRepository private constructor(
    private val memoDao: MemoDao
) {

    fun getMemoById(id: Long) =
        memoDao.getMemoById(id)

    fun getMemoAll() =
        memoDao.getMemoAll()

    fun getImagesByMid(id: Long) =
        memoDao.getImagesByMid(id)

    suspend fun updateMemo(memo: Memo) {
        memoDao.insertMemo(memo)
        Log.i("Repository","insert memo")
    }

    suspend fun updateImage(image: Image) {
        memoDao.insertImage(image)
        Log.i("Repository","insert image")
    }

    suspend fun updateMemoWithImages(memo: Memo, imageList: List<Image>) {
        memoDao.insertMemoWithImages(memo,imageList)
        Log.i("Repository","insert memo with images")
    }

    suspend fun deleteMemo(memo: Memo) {
        memoDao.deleteMemo(memo)
        Log.i("Repository","delete memo")
    }

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: MemoRepository? = null

        fun getInstance(memoDao: MemoDao) =
            instance ?: synchronized(this) {
                instance ?: MemoRepository(memoDao).also { instance = it }
            }
    }
}