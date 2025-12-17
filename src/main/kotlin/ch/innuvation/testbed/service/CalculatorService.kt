package ch.innuvation.testbed.service

import org.springframework.stereotype.Service

@Service
class CalculatorService {

    fun sum(value1: Double, value2: Double): Double =
        value1 + value2

    fun multiply(value1: Double, value2: Double): Double =
        value1 * value2
}