package org.maishameds.interviewapp.network

import android.util.Log
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException


/*
*
* This helper retrofit class is used to make safe API calls from the server
* with use of coroutines errors can be handled at one place.
* useful info {@link href = https://github.com/JobGetabu/KoinExample }
*
* */

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val errorCode: String?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
                null
            )
        }

        fun <T> error(msg: String, data: T?, errorCode: String): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg,
                errorCode
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null,
                null
            )
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}


enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    Unauthorised(401),
    ServerError(500),
    CancellationError(666),
    IOError(444)
}

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {

        //early logging, otherwise add a Log intercepter
        Log.e("Tag", "ERROR", e)

        if (e is CancellationException) {
            Resource.error(
                getErrorMessage(ErrorCodes.CancellationError.code),
                null,
                getErrorMessage(ErrorCodes.CancellationError.code)
            )
        }

        return when (e) {
            is CancellationException -> Resource.error(
                getErrorMessage(ErrorCodes.CancellationError.code),
                null,
                getErrorMessage(ErrorCodes.CancellationError.code)
            )

            is IOException -> Resource.error(
                getErrorMessage(ErrorCodes.IOError.code),
                null,
                getErrorMessage(ErrorCodes.IOError.code)
            )

            is HttpException -> Resource.error(
                getErrorMessage(e.code()),
                null,
                getErrorCode(e.code())
            )
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null,
                getErrorCode(ErrorCodes.SocketTimeOut.code)
            )

            is SocketException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null,
                getErrorCode(ErrorCodes.SocketTimeOut.code)
            )

            else -> Resource.error(
                getErrorMessage(Int.MAX_VALUE),
                null,
                getErrorCode(Int.MAX_VALUE)
            )
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            ErrorCodes.Unauthorised.code -> "Unauthorised"
            ErrorCodes.ServerError.code -> "Server Error"
            ErrorCodes.CancellationError.code -> "Request Failed"
            ErrorCodes.IOError.code -> "Check your connection"
            else -> "Something went wrong"
        }
    }

    private fun getErrorCode(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            ErrorCodes.Unauthorised.code -> "Unauthorised"
            ErrorCodes.ServerError.code -> "Server Error"
            403 -> "Retry Again"
            404 -> "Not found"
            409 -> "Conflicts found"
            else -> "Something went wrong"
        }
    }
}
