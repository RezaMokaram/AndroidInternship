package com.partSoftware.heliumplus.features.article.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.partSoftware.heliumplus.features.article.data.network.medel.TagResponse
import com.partSoftware.heliumplus.features.article.ui.model.TagView

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey
    val id: Int?,
    val name: String?,
    var isSelected: Boolean,
    var isChecked: Boolean
)

fun TagResponse.toTagEntity() = TagEntity(
    id = id,
    name = name,
    isSelected = false,
    isChecked = false
)

fun TagView.toTagEntity() = TagEntity(
    id = id,
    name = name,
    isSelected = isSelected,
    isChecked = isChecked
)