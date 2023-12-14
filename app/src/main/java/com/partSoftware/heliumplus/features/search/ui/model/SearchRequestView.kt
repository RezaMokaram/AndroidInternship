package com.partSoftware.heliumplus.features.search.ui.model

import com.partSoftware.heliumplus.features.search.data.network.model.SearchRequest

data class SearchRequestView(
    val searchedText: String
)

fun SearchRequestView.toSearchRequest() =
    SearchRequest(searchText = searchedText)