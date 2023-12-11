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

/**
 * Bastian Bastias Sanchez
 * Student ID: 301242983
 */
class BastianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: StockDataBase
    private val viewModel: StockViewModel by viewModels()
    private lateinit var selectedRadioButton: RadioButton
    private lateinit var selectedText: String


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
                    binding.radioGroup.addView(radioButton)
                }
            }
        }

        viewModel.companySelected.observe(this, Observer { selectedStock ->
            if (selectedStock != null) {
                binding.stockInfoTxt.text="Company Name: "+selectedStock.companyName.plus("\n").plus("Stock Quote: ".plus(selectedStock.stockQuote))
            }
        })

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectedRadioButton = findViewById(checkedId)
            selectedText = selectedRadioButton.text.toString()
        }

        binding.insertStockButton.setOnClickListener {
            viewModel.createDefaultStock()
        }
        binding.displayInfoButton.setOnClickListener {
            if(binding.radioGroup.childCount<1){
                Toast.makeText(this, "Please insert Stock first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.radioGroup.checkedRadioButtonId != -1){
                viewModel.getStockBySymbol(selectedText)
                Toast.makeText(this, "Selected: $selectedText", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Select a stock symbol please.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}