package com.tenderwell.toyservice.product

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductService(private val productRepository: ProductRepository) {

    @Transactional
    fun create(request: CreateProductRequest): ProductResponse {
        val product = Product(
            name = requireNotNull(request.name).trim(),
            description = request.description?.trim(),
            price = requireNotNull(request.price),
        )
        return ProductResponse.from(productRepository.save(product))
    }

    fun getById(id: Long): ProductResponse =
        productRepository.findById(id)
            .map(ProductResponse::from)
            .orElseThrow { ProductNotFoundException(id) }

    @Transactional
    fun delete(id: Long) {
        if (!productRepository.existsById(id)) throw ProductNotFoundException(id)
        productRepository.deleteById(id)
    }
}
