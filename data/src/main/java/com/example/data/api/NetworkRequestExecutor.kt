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
        Response.Failure(String.format(ErrorMessages.HTTP_ERROR, e.localizedMessage.orEmpty()))
    } catch (e: IOException) {
        // Handle IO exceptions
        Response.Failure(String.format(ErrorMessages.IO_ERROR, e.localizedMessage.orEmpty()))
    } catch (e: Exception) {
        // Handle any other exceptions
        Response.Failure(String.format(ErrorMessages.UNKNOWN_ERROR, e.localizedMessage.orEmpty()))
    }
}

object ErrorMessages {
    const val HTTP_ERROR = "HTTP error: %s"
    const val IO_ERROR = "IO error: %s"
    const val UNKNOWN_ERROR = "Unknown error: %s"
}



