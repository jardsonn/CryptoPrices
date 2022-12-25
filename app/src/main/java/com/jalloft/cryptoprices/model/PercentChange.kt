package com.jalloft.cryptoprices.model

import java.math.BigDecimal

/**
 * Created by Jardson Costa on 25/12/2022.
 */
data class PercentChange(
     val percentage: BigDecimal,
     val percentageUp: Boolean
)