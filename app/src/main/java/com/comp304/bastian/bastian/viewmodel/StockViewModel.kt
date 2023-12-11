package com.comp304.bastian.bastian.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comp304.bastian.bastian.database.StockDataBase
import com.comp304.bastian.bastian.database.entities.StockInfoEntity
import com.comp304.bastian.bastian.repo.StockRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StockViewModel: ViewModel() {
    private lateinit var database: StockDataBase
    private lateinit var repo: StockRepo

    private val _programStateFlow = MutableStateFlow(emptyList<StockInfoEntity>())
    val programStateFlow: StateFlow<List<StockInfoEntity>> = _programStateFlow.asStateFlow()


    fun getAllPrograms() {
        viewModelScope.launch {
            val programs = repo.getAllStock()
            if (programs != null) {
                _programStateFlow.update {
                    programs
                }
            }
        }
    }

    private fun createDefaultPrograms(stockRecords : List<StockInfoEntity>){
        viewModelScope.launch {
            repo.createDefaultStock(stockRecords)
        }
    }

    fun initDatabase(stockDataBase: StockDataBase) {
        Log.d("StockViewModel","initDatabase...")
        database = stockDataBase
        repo = StockRepo(database)
        createDefaultPrograms(getDefaultStock())
        getAllPrograms()
    }

    private fun getDefaultStock():List<StockInfoEntity>{
        val defaultStockRecords = ArrayList<StockInfoEntity>()
        defaultStockRecords.add(StockInfoEntity("GOOGL", "Google", 990.toDouble()))
        defaultStockRecords.add(StockInfoEntity("AMZN", "Amazon", 1290.toDouble()))
        defaultStockRecords.add(StockInfoEntity("SSNLF", "Samsung", 5990.toDouble()))
        return defaultStockRecords
    }

}