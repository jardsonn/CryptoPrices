package com.jalloft.cryptoprices.remote

import org.jsoup.Jsoup

class CurrencyDataSource: BaseDataSource(){
    suspend fun getCrytpoCurrency() = getResult {
        Jsoup.connect("https://www.coingecko.com/").get()
    }
}