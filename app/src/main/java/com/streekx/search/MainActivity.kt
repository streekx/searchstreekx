package com.streekx.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SearchAdapter
    private val supabaseUrl = "https://jhyqyskemsvoizmmupka.supabase.co/"
    private val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImpoeXF5c2tlbXN2b2l6bW11cGthIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzE4NDQ5ODUsImV4cCI6MjA4NzQyMDk4NX0.IvjAWJZ4DeOCNG0SzKgV5P-LXW2aYvX_RA-NDw5S-ec"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etSearch = findViewById<EditText>(R.id.etSearch)
        val rvResults = findViewById<RecyclerView>(R.id.rvResults)
        adapter = SearchAdapter(mutableListOf())
        rvResults.layoutManager = LinearLayoutManager(this)
        rvResults.adapter = adapter

        val api = Retrofit.Builder()
            .baseUrl(supabaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SupabaseApi::class.java)

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString().trim()
                if (query.isNotEmpty()) {
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            val results = withContext(Dispatchers.IO) {
                                api.getSearchResults("ilike.*$query*", apiKey, "Bearer $apiKey")
                            }
                            adapter.updateData(results)
                        } catch (e: Exception) {
                            Toast.makeText(this@MainActivity, "Search Error!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                true
            } else false
        }
    }
}
