package com.partSoftware.heliumplus.features.article.ui.viewModel

import androidx.lifecycle.*
import com.partSoftware.heliumplus.core.data.Constants
import com.partSoftware.heliumplus.features.article.data.repository.ArticleRepository
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor
    (
    private val articleRepository: ArticleRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val articleId: Int = savedStateHandle.get<Int>(Constants.OBJECT_ID) ?: 1

    private val _article = MutableLiveData<ArticleView>()
    val article: LiveData<ArticleView> = _article

    val isBookmarked = MutableLiveData<Boolean>(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getArticle()
            setIsBookmarked()
        }
    }

    private suspend fun getArticle() {
        _article.postValue(articleRepository.getArticle(articleId))
    }

    private fun setIsBookmarked() {
        isBookmarked.postValue(isBookmarked(articleId))
    }

    fun bookMarkArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isBookmarked(articleId)) {
                articleRepository.deleteBookmarkEntity(articleId)
                isBookmarked.postValue(false)
            } else {
                articleRepository.insertBookmarkEntity(articleId)
                isBookmarked.postValue(true)
            }
        }
    }

    fun isBookmarked(articleId: Int?) = articleRepository.isBookmarked(articleId)
}