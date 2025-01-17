package com.partSoftware.heliumplus.features.authenticate.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.core.inputValidator.InputValidator
import com.partSoftware.heliumplus.core.inputValidator.ValidationMessages
import com.partSoftware.heliumplus.features.authenticate.data.repository.AuthenticateRepository
import com.partSoftware.heliumplus.features.authenticate.ui.model.LoginRequestView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor
    (
    private val authenticateRepository: AuthenticateRepository,
    private val inputValidator: InputValidator
) :
    ViewModel() {

    var phoneNumber: String = ""
    var password: String = ""

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _loginResult = MutableLiveData<String>()
    var loginResult: LiveData<String> = _loginResult

    fun setButtonVisibility(isSuccess: Boolean) {
        _isLoading.postValue(isSuccess)
    }

    fun validateLoginInputs() =
        inputValidator.validateLoginInputs(
            phoneNumber = phoneNumber,
            password = password
        )

    fun loginUser() {

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response = authenticateRepository
                .loginUser(LoginRequestView(password, phoneNumber))) {

                is ResultWrapper.Success -> {
                    val token = response.data?.accessToken ?: ""
                    authenticateRepository.saveToken(token)
                    getAndSaveUserInfo()
                }
                is ResultWrapper.Error -> {
                    _loginResult.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _loginResult.postValue(response.errorMessage)
                }
            }
        }
    }

    private fun getAndSaveUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = authenticateRepository.getAndSaveUserInfo()) {

                is ResultWrapper.Success -> {
                    _loginResult.postValue(ValidationMessages.SUCCESSFULLY_LOGIN)
                }
                is ResultWrapper.Error -> {
                    _loginResult.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _loginResult.postValue(response.errorMessage)
                }
            }
        }
    }
}