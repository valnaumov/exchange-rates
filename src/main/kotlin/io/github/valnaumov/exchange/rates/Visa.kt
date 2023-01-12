package io.github.valnaumov.exchange.rates

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * @author Valentin Naumov
 */
@Controller("/visa")
class Visa {

    @Client
    @Inject
    lateinit var httpClient: HttpClient

    @Get(uri = "/", produces = ["text/plain"])
    fun buyRate(
        @QueryValue(value = "from", defaultValue = "KGS") from: String,
        @QueryValue(value = "to", defaultValue = "USD") to: String
    ): Double {
        val today = LocalDate.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("MM/dd/YYYY"))

        val url = "https://usa.visa.com/cmsapi/fx/rates?amount=1&fee=0" +
                "&utcConvertedDate=$today" +
                "&exchangedate=$today" +
                "&fromCurr=$from" +
                "&toCurr=$to"

        val httpRequest: MutableHttpRequest<JsonNode> = HttpRequest.GET(url)
        val responseBody = httpClient.toBlocking().retrieve(httpRequest, JsonNode::class.java)
        return (responseBody as ObjectNode)
            .get("fxRateWithAdditionalFee")
            .asDouble()
    }
}