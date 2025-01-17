package com.partSoftware.heliumplus.features.article.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.partSoftware.heliumplus.features.article.data.network.medel.ArticleResponse
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import com.partSoftware.heliumplus.features.profile.data.network.model.AuthorArticleResponse

@Entity(tableName = "article")
data class ArticleEntity(
    val authorFirstName: String?,
    val authorId: Int?,
    val authorLastName: String?,
    val authorUsername: String?,
    val content: String?,
    val createdAt: String?,
    @PrimaryKey
    val id: Int,
    val readTimeMinutes: String?,
    val tagName: String?,
    val tagId: Int?,
    val title: String?
)

fun ArticleResponse.toArticleEntity() = ArticleEntity(
    authorFirstName = authorFirstName,
    authorId = authorId,
    authorLastName = authorLastName,
    authorUsername = authorUsername,
    content = content,
    createdAt = createdAt,
    id = id ?: 0,
    readTimeMinutes = readTimeMinutes,
    tagName = tags?.first()?.name,
    tagId = tags?.first()?.id,
    title = title
)

fun ArticleView.toArticleEntity() = ArticleEntity(
    authorFirstName = authorFirstName,
    authorId = authorId,
    authorLastName = authorLastName,
    authorUsername = authorUsername,
    content = content,
    createdAt = createdAt,
    id = id ?: 0,
    readTimeMinutes = readTimeMinutes,
    tagName = tag?.name,
    tagId = tag?.id,
    title = title
)

fun AuthorArticleResponse.toArticleEntity() = ArticleEntity(
    authorFirstName = "",
    authorId = authorId,
    authorLastName = "",
    authorUsername = "",
    content = content,
    createdAt = createdAt,
    id = id ?: 0,
    readTimeMinutes = readTimeMinutes,
    tagName = tags?.first()?.name,
    tagId = tags?.first()?.id,
    title = title
)