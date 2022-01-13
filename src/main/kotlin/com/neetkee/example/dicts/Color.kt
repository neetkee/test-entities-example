package com.neetkee.example.dicts

import com.neetkee.example.NamedUUIDEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ColorTable : UUIDTable("colors") {
    val name = text("name")
}

class Color(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : NamedUUIDEntityClass<Color>(ColorTable, "Color")

    var name by ColorTable.name
}