package com.partSoftware.heliumplus.features.search.data.dataSource.remote

import com.partSoftware.heliumplus.features.search.data.network.model.SearchRequest
import com.partSoftware.heliumplus.features.search.data.network.model.SearchResponse

interface SearchRemoteDataSource {
    suspend fun getSearchResult(searchRequest: SearchRequest): SearchResponse?
}