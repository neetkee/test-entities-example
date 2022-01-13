package com.neetkee.example.product

import com.neetkee.example.dicts.Color
import com.neetkee.example.dicts.Size
import com.neetkee.example.findOrException
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService {

    fun addProduct(request: ProductChangeRequest) = transaction {
        Product.new { name = request.name }.id.value
    }

    fun editProduct(productId: UUID, request: ProductChangeRequest) = transaction {
        Product.findOrException(productId).apply { name = request.name }
    }

    fun addProductVariant(productId: UUID, request: ProductVariantChangeRequest) = transaction {
        val product = Product.findOrException(productId)
        val color = request.colorId?.let { Color.findOrException(it) }
        val size = request.sizeId?.let { Size.findOrException(it) }

        ProductVariant.new {
            this.product = product
            this.color = color
            this.size = size
        }.id.value
    }

    fun editProductVariant(productVariantId: UUID, request: ProductVariantChangeRequest) = transaction {
        val productVariant = ProductVariant.findOrException(productVariantId)
        val color = request.colorId?.let { Color.findOrException(it) }
        val size = request.sizeId?.let { Size.findOrException(it) }

        productVariant.apply {
            this.color = color
            this.size = size
        }
    }
}