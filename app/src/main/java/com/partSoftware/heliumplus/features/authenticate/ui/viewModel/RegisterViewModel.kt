package com.partSoftware.heliumplus.features.authenticate.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partSoftware.heliumplus.core.common.ResultWrapper
import com.partSoftware.heliumplus.core.inputValidator.InputValidator
import com.partSoftware.heliumplus.core.inputValidator.ValidationMessages
import com.partSoftware.heliumplus.features.authenticate.data.repository.AuthenticateRepository
import com.partSoftware.heliumplus.features.authenticate.ui.model.RegisterRequestView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authenticateRepository: AuthenticateRepository,
    private val inputValidator: InputValidator
) :
    ViewModel() {
    var firstName: String = ""
    var lastName: String = ""
    var username: String = ""
    var email: String = ""
    var phoneNumber: String = ""
    var password: String = ""

    private var _registerResult = MutableLiveData<String>()
    var registerResult: LiveData<String> = _registerResult

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun setButtonVisibility(isSuccess: Boolean) {
        _isLoading.postValue(isSuccess)
    }

    fun registerUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            when (val response = authenticateRepository
                .registerUser(
                    RegisterRequestView(
                        firstName = firstName.trim(),
                        lastName = lastName.trim(),
                        username = username.trim(),
                        email = email.trim(),
                        phoneNumber = phoneNumber.trim(),
                        password = password.trim()
                    )
                )) {

                is ResultWrapper.Success -> {
                    _registerResult.postValue(ValidationMessages.SUCCESSFULLY_REGISTER)
                }
                is ResultWrapper.Error -> {
                    _registerResult.postValue(response.errorMessage)
                }
                is ResultWrapper.Failure -> {
                    _registerResult.postValue(response.errorMessage)
                }
            }
        }
    }

    fun validateRegisterInput() = inputValidator.validateRegisterInputs(
        firstName = firstName.trim(),
        lastName = lastName.trim(),
        username = username.trim(),
        email = email.trim(),
        phoneNumber = phoneNumber.trim(),
        password = password.trim()
    )
}