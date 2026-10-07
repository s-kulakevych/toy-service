package com.tenderwell.toyservice.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = ["spring.datasource.url=jdbc:h2:mem:testdb"])
class ProductControllerTest(@Autowired private val mockMvc: MockMvc) {

    private val auth = httpBasic("admin", "admin123")

    @Test
    fun `rejects requests without credentials`() {
        mockMvc.get("/product/1").andExpect { status { isUnauthorized() } }
    }

    @Test
    fun `creates, reads and deletes a product`() {
        val location = mockMvc.post("/product") {
            with(auth)
            contentType = MediaType.APPLICATION_JSON
            content = """{"name":"Wooden train","description":"3 wagons","price":19.99}"""
        }.andExpect {
            status { isCreated() }
            jsonPath("$.id") { isNumber() }
            jsonPath("$.name") { value("Wooden train") }
        }.andReturn().response.getHeader("Location")!!

        val path = location.substringAfter("localhost")
        mockMvc.get(path) { with(auth) }.andExpect {
            status { isOk() }
            jsonPath("$.price") { value(19.99) }
        }
        mockMvc.delete(path) { with(auth) }.andExpect { status { isNoContent() } }
        mockMvc.get(path) { with(auth) }.andExpect { status { isNotFound() } }
    }

    @Test
    fun `rejects invalid payload`() {
        mockMvc.post("/product") {
            with(auth)
            contentType = MediaType.APPLICATION_JSON
            content = """{"name":"","price":-1}"""
        }.andExpect { status { isBadRequest() } }
    }

    @Test
    fun `swagger docs are public`() {
        mockMvc.get("/v3/api-docs").andExpect { status { isOk() } }
    }
}
