package com.virtualfridge.virtualfridge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VirtualFridgeApplication

fun main(args: Array<String>) {
    runApplication<VirtualFridgeApplication>(*args)
}