package io.github.valnaumov.exchange.rates

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import jakarta.inject.Singleton

private const val RATE_URL = "https://p2p.binance.com/bapi/c2c/v2/friendly/c2c/adv/search"

/**
 * @author Valentin
 */
@Singleton
class BinanceService {

    @Client
    @Inject
    lateinit var httpClient: HttpClient

    fun getRate(asset: String, fiat: String, tradeMethod: String): Double {
        val body = """{
          "proMerchantAds": false,
          "page": 1,
          "rows": 1,
          "payTypes": [
            "$tradeMethod"
          ],
          "countries": [],
          "publisherType": null,
          "asset": "$asset",
          "fiat": "$fiat",
          "tradeType": "BUY"
        }""".trimIndent()

        val httpRequest = POST(RATE_URL, body).contentType(MediaType.APPLICATION_JSON_TYPE)
        val responseBody = httpClient.toBlocking().retrieve(httpRequest, JsonNode::class.java)
        return (responseBody as ObjectNode).withArray("data")
            .first()
            .get("adv")
            .get("price")
            .asDouble()
    }
}