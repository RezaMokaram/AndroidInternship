package com.partSoftware.heliumplus.features.profile.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey
    val articleId: Int?
)