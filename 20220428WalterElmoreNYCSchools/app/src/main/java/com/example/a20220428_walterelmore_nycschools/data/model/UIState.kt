package com.example.a20220428_walterelmore_nycschools.data.model

sealed class UIState<out T>{
    object Loading: UIState<Nothing>()
    data class Error(val msg: String): UIState<Nothing>()
    data class Success<T>(val response: T): UIState<T>()
}
