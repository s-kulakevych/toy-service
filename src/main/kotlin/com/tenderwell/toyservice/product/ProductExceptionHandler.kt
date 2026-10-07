package com.tenderwell.toyservice.product

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleNotFound(ex: ProductNotFoundException): ProblemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message ?: "Not found").apply {
            title = "Product not found"
        }
}
