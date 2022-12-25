package com.jalloft.cryptoprices.remote

import com.jalloft.cryptoprices.model.Currency
import com.jalloft.cryptoprices.model.PercentChange
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.jsoup.nodes.Document
import java.io.IOException

abstract class BaseDataSource {

    protected suspend fun getResult(call: suspend () -> Document) = flow {
        try {
            emit(DataResource.loading())
            while (true){
                val document = call.invoke()
                val currencyList = mutableListOf<Currency>()
                document.select("table").select("tbody").select("tr").forEach { tr ->
                    val percentOneElement = tr.select("td")[4]
                    val percentOneDayElement = tr.select("td")[5]
                    val percentChangeSevenDayElement = tr.select("td")[6]
                    val percentChange1h = PercentChange(
                        percentOneElement.attr("data-sort").toBigDecimal(),
                        percentOneElement.select("span").hasClass("text-green")
                    )
                    val percentChange24h = PercentChange(
                        percentOneDayElement.attr("data-sort").toBigDecimal(),
                        percentOneDayElement.select("span").hasClass("text-green")
                    )
                    val percentChange7d = PercentChange(
                        percentChangeSevenDayElement.attr("data-sort").toBigDecimal(),
                        percentChangeSevenDayElement.select("span").hasClass("text-green")
                    )

                    val currency = Currency(
                        iconUrl = tr.select("td")[2].select("img").attr("src"),
                        name = tr.select("td")[2].attr("data-sort"),
                        symbols = tr.select("td")[3].select("span.no-wrap").attr("data-coin-symbol"),
                        price = tr.select("td")[3].attr("data-sort").toBigDecimal(),
                        dayValume = tr.select("td")[7].select("span").text()
                            .replace("[$,]".toRegex(), "").toBigDecimal(),
                        percentChangeInOneHour = percentChange1h,
                        percentChangeInOneDay = percentChange24h,
                        percentChangeInSevenDay = percentChange7d,
                        lastSevenDayImgUrl = tr.select("td")[11].select("a > img").attr("src")
                    )
                    currencyList.add(currency)
                }
                println("Requisitou")
                emit(DataResource.success(currencyList))
                delay(10000)
            }
        } catch (e: IOException) {
            emit(DataResource.error("Ocorreu um erro: ${e.message}"))
            e.printStackTrace()
        }

    }
}