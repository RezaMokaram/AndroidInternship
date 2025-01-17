package com.partSoftware.heliumplus.features.article.data.network.medel

import com.partSoftware.heliumplus.features.article.ui.model.AddArticleRequestView

data class AddArticleRequest(
    val content: String?,
    val tags: String?,
    val title: String?
)

fun AddArticleRequestView.toArticleRequest() = AddArticleRequest(
    content = content,
    tags = tagId,
    title = title
)