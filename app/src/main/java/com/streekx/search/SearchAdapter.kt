package com.streekx.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter(private var results: List<SearchResult>) : 
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvUrl: TextView = view.findViewById(R.id.tvUrl)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.tvTitle.text = result.title
        holder.tvUrl.text = result.link
        holder.tvDescription.text = result.description
    }

    override fun getItemCount() = results.size

    fun updateData(newResults: List<SearchResult>) {
        results = newResults
        notifyDataSetChanged()
    }
}
