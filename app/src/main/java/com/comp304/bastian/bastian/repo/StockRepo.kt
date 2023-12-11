package com.comp304.bastian.bastian.repo

import android.util.Log
import com.comp304.bastian.bastian.database.StockDataBase
import com.comp304.bastian.bastian.database.entities.StockInfoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class StockRepo(private val database: StockDataBase) {

    companion object{
        const val TAG = "StockRepo"
    }


    suspend fun getStockBySymbol(name: String) : StockInfoEntity {
        Log.e(TAG,"getStockByName: $name")
        return withContext(Dispatchers.IO) {
            database.stockInfoDao().getStockBySymbol(name)
        }
    }

    suspend fun getAllStock() : List<StockInfoEntity> {
        Log.e(TAG,"getAllStock")
        return withContext(Dispatchers.IO) {
            database.stockInfoDao().getAllStock()
        }
    }



    suspend fun createDefaultStock(stockInfoRecords : List<StockInfoEntity>){
        return withContext(Dispatchers.IO) {
            Log.e(TAG,"createDefaultStock")
            database.stockInfoDao().upsertAll(stockInfoRecords)
        }
    }
}