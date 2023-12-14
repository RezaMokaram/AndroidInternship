package com.partSoftware.heliumplus.features.search.ui.model

import com.partSoftware.heliumplus.features.article.ui.model.TagView
import com.partSoftware.heliumplus.features.search.data.network.model.TagResponseSearch

data class TagViewSearch(
    val id: Int?,
    val name: String?
)

fun TagResponseSearch.toTagVewSearch(): TagViewSearch =
    TagViewSearch(id = id, name = name)

fun TagViewSearch.toTagVew(): TagView =
    TagView(id = id, name = name, isSelected = false, isChecked = false)