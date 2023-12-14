package com.partSoftware.heliumplus.features.search.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.features.article.ui.model.ArticleView
import com.partSoftware.heliumplus.features.search.data.repository.SearchRepository
import com.partSoftware.heliumplus.features.search.ui.model.SearchRequestView
import com.partSoftware.heliumplus.features.search.ui.model.TagViewSearch
import com.partSoftware.heliumplus.features.search.ui.model.UsersViewSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) :
    ViewModel() {

    val searchText = MutableLiveData<String>()

    private var _searchResult = MutableLiveData<String>()
    var searchResult: LiveData<String> = _searchResult

    private val _articles = MutableLiveData<List<ArticleView>?>()
    val articles: LiveData<List<ArticleView>?> = _articles

    private val _tags = MutableLiveData<List<TagViewSearch>?>()
    val tags: LiveData<List<TagViewSearch>?> = _tags

    private val _users = MutableLiveData<List<UsersViewSearch>?>()
    val users: LiveData<List<UsersViewSearch>?> = _users

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getArticles(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response =
                searchRepository.getArticles(SearchRequestView(searchText.trim()))) {
                is ResultWrapper.Success -> _articles.postValue(response.data)
                is ResultWrapper.Error -> {
                    _searchResult.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _searchResult.postValue(response.errorMessage)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun getTags(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response = searchRepository.getTags(SearchRequestView(searchText.trim()))) {
                is ResultWrapper.Success -> _tags.postValue(response.data)
                is ResultWrapper.Error -> {
                    _searchResult.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _searchResult.postValue(response.errorMessage)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun getUsers(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response = searchRepository.getUsers(SearchRequestView(searchText.trim()))) {
                is ResultWrapper.Success -> _users.postValue(response.data)
                is ResultWrapper.Error -> {
                    _searchResult.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _searchResult.postValue(response.errorMessage)
                }
            }
            _isLoading.postValue(false)
        }
    }
}