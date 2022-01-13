package com.neetkee.example.builders

import com.neetkee.example.dicts.Color
import com.neetkee.example.dicts.Size
import com.neetkee.example.product.Product
import com.neetkee.example.product.ProductVariant
import com.neetkee.example.randomString
import java.util.*

class ProductBuilder : TestBuilder {
    var id: UUID = UUID.randomUUID()
    var name: String = randomString()

    val variants = mutableListOf<ProductVariantBuilder>()
    fun variant(init: ProductVariantBuilder.() -> Unit) {
        this.variants.add(initBuilder(init))
    }
}

class ProductVariantBuilder : TestBuilder {
    var id: UUID = UUID.randomUUID()
    var size: Size? = null
    var color: Color? = null

    fun size(init: SizeBuilder.() -> Unit) {
        this.size = createSize(initBuilder(init))
    }

    fun color(init: ColorBuilder.() -> Unit) {
        this.color = createColor(initBuilder(init))
    }
}

fun product(init: ProductBuilder.() -> Unit): Product {
    return createProduct(initBuilder(init))
}

fun createProduct(builder: ProductBuilder): Product {
    val product = Product.new(builder.id) {
        name = builder.name
    }
    builder.variants.forEach { variantBuilder -> createProductVariant(product, variantBuilder) }
    return product
}

fun createProductVariant(product: Product, builder: ProductVariantBuilder) {
    ProductVariant.new(builder.id) {
        this.product = product
        this.size = builder.size
        this.color = builder.color
    }
}