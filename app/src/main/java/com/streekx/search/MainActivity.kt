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
    private val allResults = mutableListOf<SearchResult>() // Crawler data store

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etSearch = findViewById<EditText>(R.id.etSearch)
        val rvResults = findViewById<RecyclerView>(R.id.rvResults)

        adapter = SearchAdapter(mutableListOf())
        rvResults.layoutManager = LinearLayoutManager(this)
        rvResults.adapter = adapter

        // Search Action (Google/Yahoo Flow)
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString().trim().lowercase()
                if (query.isNotEmpty()) {
                    performSearchFlow(query)
                }
                true
            } else false
        }
        
        loadCrawlerData() // Initialize data
    }

    private fun loadCrawlerData() {
        // Sample Data: Yahan aapka 70.3k data JSON load hoga
        allResults.add(SearchResult("Streekx Search Engine", "https://streekx.com", "The next gen search engine for everyone."))
        allResults.add(SearchResult("Android Development Guide", "https://developer.android.com", "Learn how to build native apps with Kotlin."))
        // ... baki 70k data background mein load hoga
    }

    private fun performSearchFlow(query: String) {
        // Google/Yahoo Ranking Logic:
        // 1. Pehle titles match karo
        // 2. Phir relevance ke hisaab se sort karo
        val filtered = allResults.filter { 
            it.title.lowercase().contains(query) || it.description.lowercase().contains(query)
        }.sortedByDescending { 
            it.title.lowercase().startsWith(query) // Exact match hamesha top par
        }

        if (filtered.isEmpty()) {
            Toast.makeText(this, "No matching results found in Streekx Index", Toast.LENGTH_SHORT).show()
        }
        
        adapter.updateData(filtered)
    }
}
