package ch.innuvation.testbed

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestbedApplication

fun main(args: Array<String>) {
	runApplication<TestbedApplication>(*args)
}
