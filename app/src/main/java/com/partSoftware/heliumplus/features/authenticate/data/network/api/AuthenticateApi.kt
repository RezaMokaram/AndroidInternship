package com.partSoftware.heliumplus.features.authenticate.data.network.api

import com.partSoftware.heliumplus.features.authenticate.data.network.model.LoginRequest
import com.partSoftware.heliumplus.features.authenticate.data.network.model.LoginResponse
import com.partSoftware.heliumplus.features.authenticate.data.network.model.RegisterRequest
import com.partSoftware.heliumplus.features.authenticate.data.network.model.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticateApi {

    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<Unit>

    @GET("user")
    suspend fun getUserInfo(): Response<UserInfoResponse>
}