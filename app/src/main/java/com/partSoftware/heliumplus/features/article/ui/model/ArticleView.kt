package com.partSoftware.heliumplus.features.article.ui.model

import com.partSoftware.heliumplus.core.common.convertApiTimeToJalaliCalendarDate
import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity
import com.partSoftware.heliumplus.features.article.data.network.medel.ArticleResponse

data class ArticleView(
    val authorFirstName: String?,
    val authorId: Int?,
    val authorLastName: String?,
    val authorUsername: String?,
    val content: String?,
    val createdAt: String?,
    val id: Int?,
    val readTimeMinutes: String?,
    val tag: TagView?,
    val title: String?


) {
    @JvmOverloads
    fun authorNameAndDate(isShowAuthor: Boolean = true): String {
        val jalaliData = convertApiTimeToJalaliCalendarDate(createdAt)
        return if (
            isShowAuthor &&
            !authorFirstName.isNullOrEmpty() &&
            !authorLastName.isNullOrEmpty()
        ) "$authorFirstName $authorLastName  •  $jalaliData"
        else jalaliData
    }
}

fun ArticleEntity.toArticleView() = ArticleView(
    authorFirstName = authorFirstName,
    authorId = authorId,
    authorLastName = authorLastName,
    authorUsername = authorUsername,
    content = content,
    createdAt = createdAt,
    id = id,
    readTimeMinutes = readTimeMinutes,
    tag = TagView(id = tagId, name = tagName, false, false),
    title = title
)

fun ArticleResponse.toArticleView() = ArticleView(
    authorFirstName = authorFirstName,
    authorId = authorId,
    authorLastName = authorLastName,
    authorUsername = authorUsername,
    content = content,
    createdAt = createdAt,
    id = id,
    readTimeMinutes = readTimeMinutes,
    tag = TagView(tags?.first()?.id, tags?.first()?.name),
    title = title
)