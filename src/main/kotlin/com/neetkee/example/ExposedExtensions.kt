package com.neetkee.example

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.IdTable
import java.util.*

abstract class NamedUUIDEntityClass<out E : UUIDEntity>(
    table: IdTable<UUID>,
    name: String,
    entityType: Class<E>? = null,
) : NamedEntityClass<UUID, E>(table, name, entityType)

abstract class NamedEntityClass<ID : Comparable<ID>, out E : Entity<ID>>(
    table: IdTable<ID>,
    val name: String,
    entityType: Class<E>? = null,
) : EntityClass<ID, E>(table, entityType)

fun <ID : Comparable<ID>, E : Entity<ID>> NamedEntityClass<ID, E>.findOrException(id: ID): E {
    return this.find { table.id.eq(id) }.firstOrNull()
        ?: throw NotFoundException("ID '$id' is not found for entity '$name'")
}