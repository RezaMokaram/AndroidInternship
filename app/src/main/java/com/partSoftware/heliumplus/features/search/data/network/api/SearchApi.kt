package com.partSoftware.heliumplus.features.search.data.network.api

import com.partSoftware.heliumplus.features.search.data.network.model.SearchRequest
import com.partSoftware.heliumplus.features.search.data.network.model.SearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchApi {
    @POST("search")
    suspend fun getSearchResult(@Body searchRequest: SearchRequest): Response<SearchResponse>
}