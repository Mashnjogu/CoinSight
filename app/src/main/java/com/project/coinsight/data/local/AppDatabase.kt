package com.project.coinsight.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.coinsight.data.local.dao.CoinDao
import com.project.coinsight.data.local.entity.CoinEntity

@Database(entities = [CoinEntity::class], version = 2)
abstract class AppDatabase: RoomDatabase(){
    abstract fun coinDao(): CoinDao

    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? = null

    }
}