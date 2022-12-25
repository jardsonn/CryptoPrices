package com.jalloft.cryptoprices.model

import java.math.BigDecimal


data class Currency(
    val iconUrl: String? = null,
    val name: String,
    val symbols: String,
    val price: BigDecimal?,
    val dayValume: BigDecimal?,
    val percentChangeInOneHour: PercentChange,
    val percentChangeInOneDay: PercentChange,
    val percentChangeInSevenDay: PercentChange,
    val lastSevenDayImgUrl: String?,
)