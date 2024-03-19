package com.example.domain.usecase


sealed class Response<out R> {
    data class Success<T>(val data: T) : Response<T>()
    data class Failure(val message: String) : Response<Nothing>()

}
