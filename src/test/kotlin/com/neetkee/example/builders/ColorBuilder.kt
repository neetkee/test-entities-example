package com.neetkee.example.builders

import com.neetkee.example.dicts.Color
import com.neetkee.example.randomString
import java.util.*

class ColorBuilder : TestBuilder {
    var id: UUID = UUID.randomUUID()
    var name: String = randomString()
}

fun color(init: ColorBuilder.() -> Unit): Color {
    return createColor(initBuilder(init))
}

fun createColor(builder: ColorBuilder): Color {
    return Color.new(builder.id) {
        this.name = builder.name
    }
}
