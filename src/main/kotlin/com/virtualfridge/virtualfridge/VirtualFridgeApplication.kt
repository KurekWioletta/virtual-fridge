package com.virtualfridge.virtualfridge

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class VirtualFridgeApplication

fun main(args: Array<String>) {
    SpringApplication.run(VirtualFridgeApplication::class.java, *args)
}