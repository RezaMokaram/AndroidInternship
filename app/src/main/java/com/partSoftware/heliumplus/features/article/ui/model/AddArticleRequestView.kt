package com.partSoftware.heliumplus.features.article.ui.model

import com.partSoftware.heliumplus.features.article.data.network.medel.AddArticleRequest

data class AddArticleRequestView(
    val content: String?,
    val tagId: String?,
    val title: String?
)

fun AddArticleRequest.toArticleRequestView() = AddArticleRequestView(
    content = content,
    tagId = tags,
    title = title
)