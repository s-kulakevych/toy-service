package com.tenderwell.toyservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ToyServiceApplication

fun main(args: Array<String>) {
    runApplication<ToyServiceApplication>(*args)
}
