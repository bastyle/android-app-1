package com.comp304.bastian.bastian.view

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.comp304.bastian.bastian.R
import com.comp304.bastian.bastian.broadcast.BroadcastService
import com.comp304.bastian.bastian.broadcast.LocalMessageBroadcastReceiver
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
    private lateinit var localBroadcastManager: LocalBroadcastManager
    private lateinit var localBroadcastReceiver: LocalMessageBroadcastReceiver
    private lateinit var stockInfoDetails: String


    companion object{
        val LOCAL_BROADCAST_ACTION = "LOCAL_BROADCAST_ACTION"
        val STOCK_INFO_KEY = "STOCK_INFO_KEY"
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
                stockInfoDetails="Company Name: "+selectedStock.companyName.plus("\r\n").plus("Stock Quote: ".plus(selectedStock.stockQuote))
                binding.stockInfoTxt.text=stockInfoDetails
                //
                val intent = Intent(LOCAL_BROADCAST_ACTION)
                intent.putExtra(STOCK_INFO_KEY, stockInfoDetails)
                localBroadcastManager.sendBroadcast(intent)
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
                /*val intent = Intent(LOCAL_BROADCAST_ACTION)
                intent.putExtra(STOCK_INFO_KEY, binding.stockInfoTxt.text)
                localBroadcastManager.sendBroadcast(intent)*/

            } else {
                Toast.makeText(this, "Select a stock symbol please.", Toast.LENGTH_SHORT).show()
            }
        }
        //BROADCAST
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastReceiver = LocalMessageBroadcastReceiver()

        localBroadcastManager.registerReceiver(
            localBroadcastReceiver,
            IntentFilter().apply {
                addAction(LOCAL_BROADCAST_ACTION)
            }
        )

    }
}