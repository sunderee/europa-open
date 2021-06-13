package com.peteralexbizjak.europaopen.ui.viewmodels.models

sealed class GenericResponse<out R> {
    data class Success<out T>(val data: T) : GenericResponse<T>()
    data class Error(val exception: String) : GenericResponse<Nothing>()
    object Loading : GenericResponse<Nothing>()
}