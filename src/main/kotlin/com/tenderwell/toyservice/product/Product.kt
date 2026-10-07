package com.tenderwell.toyservice.product

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products")
class Product(
    @Column(nullable = false)
    var name: String,

    @Column(length = 1000)
    var description: String? = null,

    @Column(nullable = false, precision = 19, scale = 2)
    var price: BigDecimal,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
