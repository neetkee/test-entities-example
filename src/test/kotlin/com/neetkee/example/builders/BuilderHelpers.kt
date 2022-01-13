package com.neetkee.example.builders

// See: https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker
@DslMarker
annotation class TestDsl

@TestDsl
interface TestBuilder


inline fun <reified T> initBuilder(init: T.() -> Unit): T {
    return T::class::constructors.get().first().call().also(init)
}