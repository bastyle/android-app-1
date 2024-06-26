package com.comp304.bastian.bastian.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Bastian Bastias Sanchez
 * Student ID: 301242983
 */
@Entity(tableName = "stockInfo")
data class StockInfoEntity(

    @PrimaryKey(autoGenerate = false)
    val stockSymbol: String,
    @ColumnInfo("companyName")
    val companyName: String,
    @ColumnInfo("stockQuote")
    val stockQuote: Double
) {
    override fun toString(): String {
        return stockSymbol
    }
}