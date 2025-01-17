package com.partSoftware.heliumplus.features.authenticate.ui.viewModel

import androidx.lifecycle.ViewModel
import com.partSoftware.heliumplus.features.authenticate.data.repository.AuthenticateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AuthenticateRepository
) :
    ViewModel() {

    var delayFlag = false

    fun checkTokenExist() = repository.checkTokenExist()
}