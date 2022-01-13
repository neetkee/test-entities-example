package com.neetkee.example.dicts

import com.neetkee.example.NamedUUIDEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object SizeTable : UUIDTable("sizes") {
    val name = text("name")
}

class Size(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : NamedUUIDEntityClass<Size>(SizeTable, "Size")

    var name by SizeTable.name
}