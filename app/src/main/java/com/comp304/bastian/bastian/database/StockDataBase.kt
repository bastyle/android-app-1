package com.comp304.bastian.bastian.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.comp304.bastian.bastian.database.entities.StockInfoEntity

@Database(entities = [StockInfoEntity::class] , version = 1)
abstract class StockDataBase: RoomDatabase() {

    abstract fun stockInfoDao():StockInfoDao

    companion object {
        private var instance: StockDataBase? = null

        fun getInstance(context: Context): StockDataBase {
            if (instance == null) {
                synchronized(StockDataBase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StockDataBase::class.java,
                        "StockDataBase"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return instance!!
        }
    }
}