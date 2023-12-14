package com.partSoftware.heliumplus.features.search.data.dataSource.remote

import com.partSoftware.heliumplus.core.common.bodyOrThrow
import com.partSoftware.heliumplus.features.search.data.network.api.SearchApi
import com.partSoftware.heliumplus.features.search.data.network.model.SearchRequest
import com.partSoftware.heliumplus.features.search.data.network.model.SearchResponse
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(private val searchApi: SearchApi) :
    SearchRemoteDataSource {

    override suspend fun getSearchResult(searchRequest: SearchRequest): SearchResponse? =
        searchApi.getSearchResult(searchRequest).bodyOrThrow()
}
