package ch.innuvation.testbed.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class CalculatorService {

    @Value("\${external.api.base-url:https://api.mathjs.org/v4/}")
    private lateinit var baseUrl: String

    fun sum(value1: Double, value2: Double): Double = value1 + value2
    fun multiply(value1: Double, value2: Double): Double = value1 * value2

    fun externalSqrt(value1: Double): Double {
        val client = RestClient.builder()
            .baseUrl(baseUrl.removeSuffix("/")) // e.g. http://localhost:12345 or https://api.mathjs.org/v4
            .build()

        val body = client.get()
            .uri { b ->
                b.path("/")                      // -> / or /v4/ depending on baseUrl
                    .queryParam("expr", "sqrt($value1)")
                    .build()
            }
            .retrieve()
            .body(String::class.java)!!         // mathjs returns plain text like "4" [web:2]

        return body.toDouble()
    }
}