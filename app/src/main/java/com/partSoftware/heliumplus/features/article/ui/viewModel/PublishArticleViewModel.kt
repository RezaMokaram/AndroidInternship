package com.partSoftware.heliumplus.features.article.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.features.article.data.repository.ArticleRepositoryImpl
import com.partSoftware.heliumplus.features.article.ui.model.AddArticleRequestView
import com.partSoftware.heliumplus.core.inputValidator.ValidationMessages
import com.partSoftware.heliumplus.core.inputValidator.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishArticleViewModel @Inject constructor(
    private val articleRepositoryImpl: ArticleRepositoryImpl,
    private val inputValidator: InputValidator
) : ViewModel() {
    var title: String = ""
    var content: String = ""
    var tagId: String = ""

    private var _addArticleResult = MutableLiveData<String>()
    var addArticleResult: LiveData<String> = _addArticleResult

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var isShowingBottomSheet = false

    fun setButtonVisibility(isSuccess: Boolean) {
        _isLoading.postValue(isSuccess)
    }

    fun addArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response = articleRepositoryImpl
                .addArticle(
                    AddArticleRequestView(
                        content = content,
                        title = title,
                        tagId = tagId
                    )
                )
            ) {

                is ResultWrapper.Success -> {
                    _addArticleResult.postValue(ValidationMessages.ARTICLE_SUCCESSFULLY_SENT)
                }
                is ResultWrapper.Error -> {
                    _addArticleResult.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _addArticleResult.postValue(response.errorMessage)
                }
            }
        }
    }

    fun validatePublishArticleInputs() = inputValidator.validatePublishArticleInputs(
        content = content,
        title = title,
        tagId = tagId
    )
}