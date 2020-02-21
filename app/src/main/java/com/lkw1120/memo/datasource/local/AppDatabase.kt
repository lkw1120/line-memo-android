package com.lkw1120.memo.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lkw1120.memo.datasource.entity.Image
import com.lkw1120.memo.datasource.entity.Memo
import com.lkw1120.memo.datasource.local.dao.MemoDao

@Database(entities = [Memo::class, Image::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao

    companion object {
        private const val DATABASE_NAME = "MemoApp.db"
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    }
}