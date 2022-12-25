package com.jalloft.cryptoprices.remote


class CryptoCurrencyRepository constructor(private val currencyDataSource: CurrencyDataSource) {
    suspend fun getCrytpoCurrency() = currencyDataSource.getCrytpoCurrency()
}