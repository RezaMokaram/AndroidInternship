package com.partSoftware.heliumplus.features.article.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.features.article.data.repository.ArticleRepositoryImpl
import com.partSoftware.heliumplus.features.article.ui.model.TagView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagBottomSheetViewModel @Inject constructor(
    private val articleRepositoryImpl: ArticleRepositoryImpl,
) :
    ViewModel() {

    var tagsLive: LiveData<List<TagView>?>? = articleRepositoryImpl.getTags()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _resultMessage = MutableLiveData<String>()
    val resultMessage: LiveData<String> = _resultMessage

    var isSubmitButtonClicked = false

    init {
        getTags()
    }

    fun getTags() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response = articleRepositoryImpl.fetchTags()) {
                is ResultWrapper.Error -> {
                    _resultMessage.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _resultMessage.postValue(response.errorMessage)
                }
                else -> {}
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