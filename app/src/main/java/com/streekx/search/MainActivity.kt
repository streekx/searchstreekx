package com.streekx.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: SearchAdapter
    private val allResults = mutableListOf<SearchResult>() // Yahan 70.3k data load hoga

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etSearch = findViewById<EditText>(R.id.etSearch)
        val rvResults = findViewById<RecyclerView>(R.id.rvResults)

        // Setup RecyclerView
        adapter = SearchAdapter(allResults)
        rvResults.layoutManager = LinearLayoutManager(this)
        rvResults.adapter = adapter

        // Search Action
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString().lowercase()
                filterResults(query)
                true
            } else false
        }
        
        loadCrawlerData()
    }

    private fun loadCrawlerData() {
        // Filhal ke liye testing data, yahan hum aapki crawler file connect karenge
        allResults.add(SearchResult("Streekx Official Site", "https://streekx.com", "Welcome to Streekx Search Engine."))
        // 70.3k data ko load karne ke liye hum JSON ka use karenge
    }

    private fun filterResults(query: String) {
        val filteredList = allResults.filter { 
            it.title.lowercase().contains(query) || it.description.lowercase().contains(query) 
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
        }
        adapter.updateData(filteredList)
    }
}

