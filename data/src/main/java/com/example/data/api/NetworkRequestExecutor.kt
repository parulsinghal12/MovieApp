package com.example.data.api

import com.example.domain.usecase.Response
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> executeNetworkRequest(
    request: suspend () -> Response<T>
): Response<T>  {
    return try {
        // Execute the API call and return success
        request()
    } catch (e: HttpException) {
        // Handle Retrofit's HttpExceptions
        Response.Failure("Network error: ${e.localizedMessage.orEmpty()}")
    } catch (e: IOException) {
        // Handle IO exceptions
        Response.Failure("IO error: ${e.localizedMessage.orEmpty()}")
    } catch (e: Exception) {
        // Handle any other exceptions
        Response.Failure("Unknown error: ${e.localizedMessage.orEmpty()}")
    }
}

