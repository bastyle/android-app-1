package com.comp304.bastian.bastian.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.comp304.bastian.bastian.database.entities.StockInfoEntity
/**
 * Bastian Bastias Sanchez
 * Student ID: 301242983
 */
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
            WHERE stockSymbol = :name
        """
    )
    fun getStockBySymbol(name: String): StockInfoEntity
}