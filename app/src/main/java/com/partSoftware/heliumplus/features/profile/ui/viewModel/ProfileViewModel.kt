package com.partSoftware.heliumplus.features.profile.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.partSoftware.heliumplus.core.data.Constants
import com.partSoftware.heliumplus.features.profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    profileRepository: ProfileRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    val userId: Int = getUserIdArgument()
        ?: profileRepository.getUserInfo().id ?: 1

    val userFullName: String = savedStateHandle.get<String>(Constants.USER_FULL_NAME)
        ?: "${profileRepository.getUserInfo().firstName} ${profileRepository.getUserInfo().lastName}"

    fun getUserIdArgument(): Int? = savedStateHandle.get<Int>(Constants.USER_ID)
}