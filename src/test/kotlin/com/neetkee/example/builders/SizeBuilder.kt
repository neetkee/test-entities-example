package com.neetkee.example.builders

import com.neetkee.example.dicts.Size
import com.neetkee.example.randomString
import java.util.*

class SizeBuilder : TestBuilder {
    var id: UUID = UUID.randomUUID()
    var name: String = randomString()
}

fun size(init: SizeBuilder.() -> Unit): Size {
    return createSize(initBuilder(init))
}

fun createSize(builder: SizeBuilder): Size {
    return Size.new(builder.id) {
        this.name = builder.name
    }
}