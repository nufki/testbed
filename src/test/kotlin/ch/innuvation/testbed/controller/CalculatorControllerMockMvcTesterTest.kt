package ch.innuvation.testbed.controller

import ch.innuvation.testbed.service.CalculatorService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.assertj.MockMvcTester
import org.assertj.core.api.Assertions.assertThat
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.mockito.kotlin.whenever
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock

@AutoConfigureWireMock(
    port = 0,
    stubs = ["classpath:/wiremock/mappings"],
    files = ["classpath:/wiremock"]
)
@WebMvcTest(controllers = [CalculatorController::class])
class CalculatorControllerMockMvcTesterTest(
) {

    @Autowired
    private lateinit var mockMvcTester: MockMvcTester

    @MockitoBean
    private lateinit var calculatorService: CalculatorService


    @Test
    fun `sum endpoint returns result`() {
        whenever(calculatorService.sum(4.0, 2.0)).thenReturn(6.0)

        val result = mockMvcTester
            .get()
            .uri ("/calculator/sum")
            .queryParam("value1", "4.0")
            .queryParam("value2", "2.0")

        assertThat(result)
            .hasStatusOk()
            .hasBodyTextEqualTo("6.0")
    }

    @Test
    fun `multiply endpoint returns result`() {
        whenever(calculatorService.multiply(3.0, 4.0)).thenReturn(12.0)

        val result = mockMvcTester
            .get()
            .uri ("/calculator/multiply")
            .queryParam("value1", "3.0")
            .queryParam("value2", "4.0")

        assertThat(result)
            .hasStatusOk()
            .hasBodyTextEqualTo("12.0")
    }
}
