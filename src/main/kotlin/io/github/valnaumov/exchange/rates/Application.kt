package io.github.valnaumov.exchange.rates

import io.micronaut.runtime.Micronaut.*
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*

@OpenAPIDefinition(
    info = Info(
            title = "exchange-rates",
            version = "0.0"
    )
)
object Api {
}

fun main(args: Array<String>) {
	run(*args)
}

