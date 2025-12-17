package ch.innuvation.testbed

import ch.innuvation.testbed.service.CalculatorService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient

data class HelloResponse(
    val text: String
)

@SpringBootTest
@AutoConfigureWireMock(
    port = 0,
    stubs = ["classpath:/wiremock/mappings"],
    files = ["classpath:/wiremock"],
)

@AutoConfigureMockMvc
@ActiveProfiles("test")
open class SimpleWiremokTest{

    @Value("\${external.api.base-url}")
    private lateinit var wireMockBaseUrl: String

    @Autowired
    private lateinit var calculatorService: CalculatorService

    private val client: RestClient by lazy {
        RestClient.builder()
            .baseUrl(wireMockBaseUrl)
            .build()
    }

    @Test
    fun mockAPICall() {
        val body = client.get()
            .uri("/hello")
            .retrieve()
            .body(HelloResponse::class.java)


        assertThat(body).isNotNull
        assertThat(body!!.text).isEqualTo("Hello from WireMock (file)!") // RestClient uses Jackson under the hood
    }

    @Test
    fun `direct service test via WireMock`() {
        // Test service layer isolation
        val result = calculatorService.externalSqrt(16.0)
        assertThat(result).isEqualTo(4.0)
    }
}
