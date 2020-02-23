package com.lkw1120.memo.utils

import android.content.Context
import com.lkw1120.memo.datasource.entity.MemoWithImages
import com.lkw1120.memo.datasource.local.AppDatabase
import com.lkw1120.memo.repository.MemoRepository
import com.lkw1120.memo.viewmodel.DetailViewModelFactory
import com.lkw1120.memo.viewmodel.MainViewModelFactory
import com.lkw1120.memo.viewmodel.WriteViewModelFactory

object InjectorUtils {

    private fun getMemoRepository(context: Context): MemoRepository {
        return MemoRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).memoDao()
        )
    }

    fun provideDetailViewModelFactory(
        context: Context,
        id: Long
    ): DetailViewModelFactory {
        val memoRepository = getMemoRepository(context)
        return DetailViewModelFactory(memoRepository,id)
    }

    fun provideMainViewModelFactory(
        context: Context
    ): MainViewModelFactory {
        val memoRepository = getMemoRepository(context)
        return MainViewModelFactory(memoRepository)
    }

    fun provideWriteViewModelFactory(
        context: Context,
        memoWithImages: MemoWithImages
    ): WriteViewModelFactory {
        val memoRepository = getMemoRepository(context)
        return WriteViewModelFactory(memoRepository, memoWithImages)
    }
}
