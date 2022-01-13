package com.neetkee.example.product

import com.neetkee.example.NamedUUIDEntityClass
import com.neetkee.example.dicts.Color
import com.neetkee.example.dicts.ColorTable
import com.neetkee.example.dicts.Size
import com.neetkee.example.dicts.SizeTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ProductTable : UUIDTable("products") {
    val name = text("name")
}

class Product(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : NamedUUIDEntityClass<Product>(ProductTable, "Product")

    var name by ProductTable.name
}

object ProductVariantTable : UUIDTable("product_variants") {
    val productId = reference("product_id", ProductTable)
    val sizeId = reference("size_id", SizeTable).nullable()
    val colorId = reference("color_id", ColorTable).nullable()
}

class ProductVariant(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : NamedUUIDEntityClass<ProductVariant>(ProductVariantTable, "Product variant")

    var product by Product referencedOn ProductVariantTable.productId
    var size by Size optionalReferencedOn ProductVariantTable.sizeId
    var color by Color optionalReferencedOn ProductVariantTable.colorId
}