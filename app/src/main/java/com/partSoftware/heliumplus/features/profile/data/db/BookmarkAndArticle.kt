package com.partSoftware.heliumplus.features.profile.data.db

import androidx.room.Embedded
import androidx.room.Relation
import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity

data class BookmarkAndArticle(
    @Embedded val bookmarkEntity: BookmarkEntity,
    @Relation(
        parentColumn = "articleId",
        entityColumn = "id"
    )
    val articleEntity: ArticleEntity
)
