package com.comp304.bastian.bastian.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.comp304.bastian.bastian.database.entities.StockInfoEntity

@Dao
interface StockInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun upsertAll(userList: List<StockInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(program: StockInfoEntity)


    @Query(
        """
            SELECT * FROM stockInfo
        """
    )
    fun getAllStock(): List<StockInfoEntity>


    @Query(
        """
            SELECT * FROM stockInfo
            WHERE companyName = :name
        """
    )
    fun getStockByName(name: String): StockInfoEntity
}