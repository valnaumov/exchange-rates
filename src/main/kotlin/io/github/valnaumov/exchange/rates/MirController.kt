package io.github.valnaumov.exchange.rates

import io.micronaut.cache.annotation.Cacheable
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.jsoup.Jsoup

private const val ratesUrl = "https://mironline.ru/support/list/kursy_mir/"

/**
 * @author Valentin
 */
@Controller("/mir")
class MirController {

    @Inject
    lateinit var mirService: MirService

    @Get(uri = "/{code}", produces = ["text/plain"])
    fun rate(@PathVariable("code") currency: Currency): Double {
        return mirService.getCurrentRate(currency)
    }

    @Get(uri = "/currencies")
    fun currencies(): List<String> {
        return Currency.values().map { it.name }
    }
}

@Singleton
open class MirService {

    @Cacheable("mirCurrentRate")
    open fun getCurrentRate(currency: Currency): Double {
        return Jsoup.connect(ratesUrl).get().select(currency.selector).text().replace(",", ".").toDouble()
    }
}

enum class Currency(val selector: String) {
    AMD(".sf-text > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(2) > td:nth-child(2) > span:nth-child(1) > p:nth-child(1)"),
    BYN(".sf-text > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(3) > td:nth-child(2) > span:nth-child(1) > p:nth-child(1)"),
    VND(".sf-text > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(4) > td:nth-child(2) > span:nth-child(1) > p:nth-child(1)"),
    KZT(".sf-text > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(5) > td:nth-child(2) > span:nth-child(1) > p:nth-child(1)"),
    KGS(".sf-text > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(6) > td:nth-child(2) > span:nth-child(1) > p:nth-child(1)"),
    TJS(".sf-text > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(7) > td:nth-child(2) > span:nth-child(1) > p:nth-child(1)"),
    UZS(".sf-text > table:nth-child(2) > tbody:nth-child(2) > tr:nth-child(8) > td:nth-child(2) > span:nth-child(1) > p:nth-child(1)");
}
