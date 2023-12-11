package com.comp304.bastian.bastian.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.comp304.bastian.bastian.R
import com.comp304.bastian.bastian.database.StockDataBase
import com.comp304.bastian.bastian.databinding.ActivityMainBinding
import com.comp304.bastian.bastian.viewmodel.StockViewModel
import kotlinx.coroutines.launch

class BastianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: StockDataBase
    private val viewModel: StockViewModel by viewModels()

    //private lateinit var adapter: ProgramActivityViewAdapter
    companion object{
        const val TAG = "BastianActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"oncreate...")
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        database = StockDataBase.getInstance(baseContext)
        viewModel.initDatabase(database)

        lifecycleScope.launch {
            viewModel.stockStateFlow.collect {
                it.forEach{
                    val radioButton = RadioButton(baseContext)
                    radioButton.text = it.toString()
                    radioButton.id = it.stockSymbol.hashCode()
                    binding.radioGroup.addView(radioButton)
                }
            }
        }
    }
}