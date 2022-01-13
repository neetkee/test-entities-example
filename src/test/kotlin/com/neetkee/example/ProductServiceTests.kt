package com.neetkee.example

import com.neetkee.example.builders.color
import com.neetkee.example.builders.product
import com.neetkee.example.builders.size
import com.neetkee.example.product.*
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class ProductServiceTests(
    @Autowired private val productService: ProductService
) : BaseIntegrationTest() {

    @Test
    fun `should add product`() {
        val request = ProductChangeRequest(name = randomString())
        val productId = productService.addProduct(request)
        transaction {
            val product = Product.findOrException(productId)
            product.name.shouldBe(request.name)
        }
    }

    @Test
    fun `should edit product`() {
        val productId = UUID.randomUUID()
        transaction {
            product { id = productId }
        }

        val request = ProductChangeRequest(name = randomString())
        productService.editProduct(productId, request)
        transaction {
            val product = Product.findOrException(productId)
            product.name.shouldBe(request.name)
        }
    }

    @Test
    fun `should add product variant`() {
        val productId = UUID.randomUUID()
        val colorId = UUID.randomUUID()
        val sizeId = UUID.randomUUID()
        transaction {
            product { id = productId }
            color { id = colorId }
            size { id = sizeId }
        }

        val request = ProductVariantChangeRequest(colorId = colorId, sizeId = sizeId)
        val productVariantId = productService.addProductVariant(productId, request)
        transaction {
            val productVariant = ProductVariant.findOrException(productVariantId)
            productVariant.product.id.value.shouldBe(productId)
            productVariant.size.shouldNotBeNull().id.value.shouldBe(sizeId)
            productVariant.color.shouldNotBeNull().id.value.shouldBe(colorId)
        }
    }

    @Test
    fun `should edit product variant`() {
        val productVariantId = UUID.randomUUID()
        val colorId = UUID.randomUUID()
        val sizeId = UUID.randomUUID()
        transaction {
            val color = color { id = colorId }
            val size = size { id = sizeId }
            product {
                // empty variant
                variant {}
                // variant with color and size
                variant { color {}; size {} }
                // variant to edit
                variant {
                    id = productVariantId
                    this.color = color
                    this.size = size
                }
            }
        }
        // let's check that builder works
        transaction {
            val productVariant = ProductVariant.findOrException(productVariantId)
            productVariant.size.shouldNotBeNull()
            productVariant.color.shouldNotBeNull()
        }

        val request = ProductVariantChangeRequest(colorId = colorId, sizeId = sizeId)
        productService.editProductVariant(productVariantId, request)
        transaction {
            val productVariant = ProductVariant.findOrException(productVariantId)
            productVariant.size.shouldNotBeNull().id.value.shouldBe(sizeId)
            productVariant.color.shouldNotBeNull().id.value.shouldBe(colorId)
        }
    }

    @Test
    fun `multiple entities`() {
        transaction {
            color {}
            size {}
            product {
                variant {}
                variant { color {} }
                variant { color {}; size {} }
            }
            product {
                name = "productName"
                variant { size { name = "sizeName" } }
            }
        }
    }
}