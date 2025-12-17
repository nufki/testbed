package ch.innuvation.testbed

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(
    port = 0,
    stubs = ["classpath:/wiremock/mappings"],
    files = ["classpath:/wiremock"]
)
@ActiveProfiles("test")
class CalculatorWebTestClientWiremockTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `sqrt endpoint calls external API via WireMock`() {
        mockMvc.get("/calculator/sqrt") {
            param("value1", "16.0")
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.result").value(4.0)
        }
    }
}
