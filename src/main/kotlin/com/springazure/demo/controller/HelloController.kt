package com.springazure.demo.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController {

    @Value("\${connectionString}")
    private var connectionString = ""

    @RequestMapping("/")
    fun index(): String? {
        return "Greetings from Spring Boot! To implement Azure next"
    }

    @GetMapping("get")
    fun get(): String? {
        return connectionString
    }

    @Throws(Exception::class)
    fun run(vararg varl: String?) {
        println(String.format("\nConnection String stored in Azure Key Vault:\n%s\n", connectionString))
    }
}