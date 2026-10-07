package com.tenderwell.toyservice.product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/product")
@Tag(name = "Product", description = "Create, read and delete products")
@SecurityRequirement(name = "basicAuth")
@ApiResponses(ApiResponse(responseCode = "401", description = "Missing or invalid credentials", content = [Content()]))
class ProductController(private val productService: ProductService) {

    @PostMapping
    @Operation(summary = "Create a product")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Product created"),
        ApiResponse(responseCode = "400", description = "Invalid payload", content = [Content()]),
    )
    fun create(@Valid @RequestBody request: CreateProductRequest): ResponseEntity<ProductResponse> {
        val created = productService.create(request)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(created.id).toUri()
        return ResponseEntity.created(location).body(created)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by id")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Product found"),
        ApiResponse(responseCode = "404", description = "Product not found", content = [Content()]),
    )
    fun getById(@Parameter(description = "Product id", example = "1") @PathVariable id: Long): ProductResponse =
        productService.getById(id)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product by id")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Product deleted"),
        ApiResponse(responseCode = "404", description = "Product not found", content = [Content()]),
    )
    fun delete(@Parameter(description = "Product id", example = "1") @PathVariable id: Long) =
        productService.delete(id)
}
