package com.partSoftware.heliumplus.features.profile.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import com.partSoftware.heliumplus.features.profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyArticleViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    ViewModel() {

    private val _authorArticles = MutableLiveData<List<ArticleView>?>()
    val authorArticles: LiveData<List<ArticleView>?> = _authorArticles

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _resultMessage = MutableLiveData<String>()
    val resultMessage: LiveData<String> = _resultMessage

    fun getAuthorArticles(authorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response = profileRepository.getAuthorArticles(authorId)) {
                is ResultWrapper.Success -> _authorArticles.postValue(response.data)
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

}