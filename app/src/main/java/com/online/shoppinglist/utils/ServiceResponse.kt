package com.online.shoppinglist.utils

/**
 * Generic Response handler created to handle the api Response
 * inside the repositories.
 */
data class ServiceResponse<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ServiceResponse<T> {
            return ServiceResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ServiceResponse<T> {
            return ServiceResponse(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ServiceResponse<T> {
            return ServiceResponse(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

