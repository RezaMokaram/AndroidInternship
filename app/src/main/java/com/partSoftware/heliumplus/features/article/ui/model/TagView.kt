package com.partSoftware.heliumplus.features.article.ui.model

import com.partSoftware.heliumplus.features.article.data.db.entity.TagEntity

data class TagView(
    val id: Int?,
    val name: String?,
    var isSelected: Boolean = false,
    var isChecked: Boolean = false
)

fun TagEntity.toTagView() = TagView(
    id = id,
    name = name,
    isSelected = isSelected,
    isChecked = isChecked
)