package com.partSoftware.heliumplus.features.profile.ui.viewModel

import androidx.lifecycle.ViewModel
import com.partSoftware.heliumplus.features.profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    profileRepository: ProfileRepository
) :
    ViewModel() {

    // private val _bookmarkedArticles = LiveData<List<ArticleView>?>
    val bookmarkedArticles = profileRepository.getBookmarkedArticles()
}