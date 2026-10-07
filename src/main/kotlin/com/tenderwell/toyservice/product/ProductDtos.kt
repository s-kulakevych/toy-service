package com.tenderwell.toyservice.product

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

@Schema(description = "Payload for creating a product")
data class CreateProductRequest(
    @field:NotBlank
    @field:Size(max = 255)
    @Schema(description = "Product name", example = "Wooden train")
    val name: String? = null,

    @field:Size(max = 1000)
    @Schema(description = "Optional description", example = "Classic wooden toy train with 3 wagons")
    val description: String? = null,

    @field:NotNull
    @field:DecimalMin(value = "0.00")
    @Schema(description = "Price, non-negative", example = "19.99")
    val price: BigDecimal? = null,
)

@Schema(description = "A stored product")
data class ProductResponse(
    @Schema(example = "1") val id: Long,
    @Schema(example = "Wooden train") val name: String,
    @Schema(example = "Classic wooden toy train with 3 wagons") val description: String?,
    @Schema(example = "19.99") val price: BigDecimal,
) {
    companion object {
        fun from(product: Product) = ProductResponse(
            id = requireNotNull(product.id) { "Product is not persisted" },
            name = product.name,
            description = product.description,
            price = product.price,
        )
    }
}
