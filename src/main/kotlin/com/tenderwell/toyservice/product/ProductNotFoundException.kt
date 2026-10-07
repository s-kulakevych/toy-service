package com.tenderwell.toyservice.product

class ProductNotFoundException(val id: Long) : RuntimeException("Product with id $id not found")
