package be.fgov.onemrva.kotlin_sandbox.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/")
    fun hello(): Map<String, String> {
        return mapOf("message" to "Hello from Kotlin Spring Boot!")
    }
}
