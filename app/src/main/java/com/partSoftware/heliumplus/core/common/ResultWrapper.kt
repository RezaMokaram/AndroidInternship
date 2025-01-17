package com.partSoftware.heliumplus.core.common

sealed class ResultWrapper<out T> {

    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error(val errorMessage: String) : ResultWrapper<Nothing>()
    data class Failure(val errorMessage: String) : ResultWrapper<Nothing>()
}