package ch.innuvation.testbed.controller

import ch.innuvation.testbed.service.CalculatorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/calculator")
class CalculatorController(
    private val calculatorService: CalculatorService
) {

    @GetMapping("/sum")
    fun sum(
        @RequestParam value1: Double,
        @RequestParam value2: Double
    ): Double {
        return calculatorService.sum(value1, value2)
    }

    @GetMapping("/multiply")
    fun multiply(
        @RequestParam value1: Double,
        @RequestParam value2: Double
    ): Double {
        return calculatorService.multiply(value1, value2)
    }
}
