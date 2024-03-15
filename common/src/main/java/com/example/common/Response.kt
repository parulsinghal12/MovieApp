package com.example.common


sealed class Response<out R> { //parul: doubt - to understand better about sealed & in & out, covariance
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
    object Loading : Response<Nothing>()
}
