package com.comp304.bastian.bastian.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.comp304.bastian.bastian.R
import com.comp304.bastian.bastian.database.StockDataBase
import com.comp304.bastian.bastian.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class BastianActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: StockDataBase
    private val viewModel: ProgramsViewModel by viewModels()

    private lateinit var adapter: ProgramActivityViewAdapter
    companion object{
        const val TAG = "BastianActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"oncreate...")
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        database = CollegeDataBase.getInstance(baseContext)
        viewModel.initDatabase(database, GlobalUtil.loadDataFromJson(this))

        adapter = ProgramActivityViewAdapter(this)

        binding.recyclerView.adapter=this.adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)
        lifecycleScope.launch {
            viewModel.programStateFlow.collect {
                adapter.updateList(it)
            }
        }
    }
}