package com.partSoftware.heliumplus.features.search.data.network.model

import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("search_text")
    val searchText: String
)
