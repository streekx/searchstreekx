package com.streekx.search

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SupabaseApi {
    // Note: Yahan table ka naam 'search_results' ya jo bhi aapne rakha hai wo hoga
    @GET("rest/v1/search_results") 
    suspend fun getSearchResults(
        @Query("title") searchQuery: String,
        @Header("apikey") apiKey: String,
        @Header("Authorization") bearer: String
    ): List<SearchResult>
}

