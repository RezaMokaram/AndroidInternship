package com.partSoftware.heliumplus.features.authenticate.ui.model


data class RegisterRequestView(
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val password: String?,
    val phoneNumber: String?,
    val username: String?
)