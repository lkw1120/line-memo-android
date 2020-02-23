package com.lkw1120.memo.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lkw1120.memo.datasource.entity.Image
import com.lkw1120.memo.datasource.entity.Memo

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo")
    fun getMemoAll(): LiveData<List<Memo>>

    @Query("SELECT * FROM memo WHERE memo.memo_id = :id")
    fun getMemoById(id: Long): LiveData<Memo>

    @Query("SELECT * FROM image WHERE image.memo_id = :mid ORDER BY image_order ASC")
    fun getImagesByMid(mid: Long): LiveData<List<Image>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: Image)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageAll(images: List<Image>)

    @Transaction
    suspend fun insertMemoWithImages(memo: Memo, images: List<Image>) {
        insertMemo(memo)
        insertImageAll(images)
    }

    @Delete
    suspend fun deleteMemo(memo: Memo)
}