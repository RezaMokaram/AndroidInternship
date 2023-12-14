package com.partSoftware.heliumplus.features.article.ui.model

import com.partSoftware.heliumplus.features.article.data.network.medel.AddArticleResponse

data class AddArticleResponseView(
    val articleId: Int?,
    val message: String?
)

fun AddArticleResponse.toAddArticleResponseView() = AddArticleResponseView(
    articleId = articleId,
    message = message
)