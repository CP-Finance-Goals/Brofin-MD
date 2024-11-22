package com.example.brofin.domain

sealed class StateApp<out T> {
    data class Success<out T>(val data: T) : StateApp<T>()
    data class Error(val exception: String) : StateApp<Nothing>()
    data object Loading : StateApp<Nothing>()
    data object Idle : StateApp<Nothing>()
}
