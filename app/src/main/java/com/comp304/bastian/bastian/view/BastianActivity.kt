package com.comp304.bastian.bastian.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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
                it.forEach{ infoEntity ->
                    val radioButton = RadioButton(baseContext)
                    radioButton.text = infoEntity.toString()
                    radioButton.id = infoEntity.stockSymbol.hashCode()
                    /*radioButton.setOnClickListener {
                        Toast.makeText(this@BastianActivity, infoEntity.toString(), Toast.LENGTH_SHORT).show()
                        binding.stockInfoTxt.text="Company Name: "+infoEntity.companyName.plus("\n").plus("Stock Quote: ".plus(infoEntity.stockQuote))
                    }*/

                    binding.radioGroup.addView(radioButton)
                }
            }
        }
        // Observe the companySelected LiveData
        viewModel.companySelected.observe(this, Observer { selectedStock ->
            if (selectedStock != null) {
                binding.stockInfoTxt.text="Company Name: "+selectedStock.companyName.plus("\n").plus("Stock Quote: ".plus(selectedStock.stockQuote))
            }
        })

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton: RadioButton = findViewById(checkedId)
            val selectedText: String = selectedRadioButton.text.toString()
            Toast.makeText(this, "Selected: $selectedText", Toast.LENGTH_SHORT).show()
            viewModel.getStockBySymbol(selectedText)
        }

        binding.insertStockButton.setOnClickListener {
            viewModel.createDefaultStock()
        }
        binding.displayInfoButton.setOnClickListener {

        }
    }
}