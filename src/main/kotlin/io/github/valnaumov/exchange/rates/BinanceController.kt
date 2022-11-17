package io.github.valnaumov.exchange.rates

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import jakarta.inject.Inject

/**
 * @author Valentin
 */
@Controller("/binance")
class BinanceController {

    @Inject
    lateinit var binanceService: BinanceService

    @Get(uri = "/buy", produces = ["text/plain"])
    fun buyRate(
        @QueryValue(value = "asset", defaultValue = "USDT") asset: String,
        @QueryValue(value = "fiat", defaultValue = "RUB") fiat: String,
        @QueryValue(value = "tradeMethod", defaultValue = "TinkoffNew") tradeMethod: String
    ): Double {
        return binanceService.getBuyRate(asset, fiat, tradeMethod)
    }

//    fun tradeMethods(): JsonArray {
//
//    }
}