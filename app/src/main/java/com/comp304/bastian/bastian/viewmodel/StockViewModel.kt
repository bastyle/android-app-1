package com.comp304.bastian.bastian.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comp304.bastian.bastian.database.StockDataBase
import com.comp304.bastian.bastian.database.entities.StockInfoEntity
import com.comp304.bastian.bastian.repo.StockRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StockViewModel: ViewModel() {
    private lateinit var database: StockDataBase
    private lateinit var repo: StockRepo

    private val _stockStateFlow = MutableStateFlow(emptyList<StockInfoEntity>())
    val stockStateFlow: StateFlow<List<StockInfoEntity>> = _stockStateFlow.asStateFlow()

    private val _companySelected = MutableLiveData<StockInfoEntity>()
    val companySelected : MutableLiveData<StockInfoEntity> get()= _companySelected


    fun getStock() {
        viewModelScope.launch {
            val stockRecords = repo.getAllStock()
            if (stockRecords != null) {
                _stockStateFlow.update {
                    stockRecords
                }
            }
        }
    }

    fun createDefaultStock(stockRecords : List<StockInfoEntity>){
        viewModelScope.launch {
            repo.createDefaultStock(stockRecords)
            getStock()
        }
    }

    fun createDefaultStock(){
        viewModelScope.launch {
            repo.createDefaultStock(getDefaultStock())
            getStock()
        }
    }

    fun getStockBySymbol(stockCompanySymbol:String) {
        viewModelScope.launch {
            _companySelected.postValue(repo.getStockBySymbol(stockCompanySymbol))
        }
    }

    fun initDatabase(stockDataBase: StockDataBase) {
        Log.d("StockViewModel","initDatabase...")
        database = stockDataBase
        repo = StockRepo(database)
        //createDefaultStock(getDefaultStock())
        //getStock()
    }

    private fun getDefaultStock():List<StockInfoEntity>{
        val defaultStockRecords = ArrayList<StockInfoEntity>()
        defaultStockRecords.add(StockInfoEntity("GOOGL", "Google", 990.toDouble()))
        defaultStockRecords.add(StockInfoEntity("AMZN", "Amazon", 1290.toDouble()))
        defaultStockRecords.add(StockInfoEntity("SSNLF", "Samsung", 5990.toDouble()))
        return defaultStockRecords
    }

}