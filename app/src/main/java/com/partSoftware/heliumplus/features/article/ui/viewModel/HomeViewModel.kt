package com.partSoftware.heliumplus.features.article.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.features.article.data.repository.ArticleRepository
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import com.partSoftware.heliumplus.features.article.ui.model.TagView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleRepositoryImpl: ArticleRepository
) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleView>?>()
    val articles: LiveData<List<ArticleView>?> = _articles

    val selectedTags: LiveData<List<TagView>?>? =
        articleRepositoryImpl.getSelectedTags()

    val checkedTagsIdsLiveData = articleRepositoryImpl.getCheckedTagsIdsLiveData()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _resultMessage = MutableLiveData<String>()
    val resultMessage: LiveData<String> = _resultMessage

    var isShowingBottomSheet = false

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val checkedTagsIds = articleRepositoryImpl.getCheckedTagsIds()
            if (checkedTagsIds.isEmpty())
                getArticles()
            else
                getArticleByTags(checkedTagsIds)
        }
    }


    fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response = articleRepositoryImpl.getArticles()) {
                is ResultWrapper.Success -> _articles.postValue(response.data)
                is ResultWrapper.Error -> {
                    _resultMessage.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _resultMessage.postValue(response.errorMessage)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun getArticleByTags(checkedTagsIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response = articleRepositoryImpl.getArticlesByTag(checkedTagsIds)) {
                is ResultWrapper.Success -> _articles.postValue(response.data)
                is ResultWrapper.Error -> {
                    _resultMessage.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _resultMessage.postValue(response.errorMessage)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun updateTags(tags: List<TagView>) {
        viewModelScope.launch(Dispatchers.IO) {
            articleRepositoryImpl.updateTags(tags)
        }
    }
}