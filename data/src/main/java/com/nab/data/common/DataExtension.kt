package com.nab.data.common

import android.accounts.NetworkErrorException
import com.nab.data.entities.ForecastResponse
import com.nab.domain.entities.ErrorModel
import com.nab.domain.entities.ForecastResult
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

/**
 * Created by tho nguyen on 6/16/2021.
 * dinhthohcmus@gmail.com
 */

private const val NETWORK_CONNECTION_ERROR = "Network connection error"
private const val NOT_FOUND = "Not found resource"

suspend fun <T> runWithCatchError(
    call: suspend () -> T,
    success: suspend (T) -> Unit,
    failed: suspend (ForecastResult.Failed) -> Unit
) {
    try {
        success.invoke(call.invoke())
    } catch (exception: Exception) {
        failed.invoke(exception.toForecastResultFailed())
    }
}

fun Exception.toForecastResultFailed(): ForecastResult.Failed {
    val error = when (this) {
        is NetworkErrorException, is IOException, is UnknownHostException -> {
            ErrorModel(NETWORK_CONNECTION_ERROR)
        }
        is HttpException -> {
            if (code() == 404) {
                ErrorModel(NOT_FOUND)
            } else {
                ErrorModel(message())
            }
        }
        else -> {
            ErrorModel(message)
        }
    }
    return ForecastResult.Failed(error)
}