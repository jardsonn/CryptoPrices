package com.jalloft.cryptoprices.remote

import com.jalloft.cryptoprices.model.Currency

/**
 * Created by Jardson Costa on 28/11/2021.
 */

data class DataResource<out T>(val status: Status, val data: T?, val message: String?){
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun success(data: List<Currency>): DataResource<List<Currency>> {
            return DataResource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): DataResource<T> {
            return DataResource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): DataResource<T> {
            return DataResource(Status.LOADING, data, null)
        }
    }
}