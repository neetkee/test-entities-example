package com.neetkee.example

import kotlin.random.Random
import kotlin.streams.asSequence

fun randomString(upTo: Int = 20, from: Int = 6): String {
    val size = Random.nextInt(from = from, until = upTo)
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    return java.util.Random().ints(size.toLong(), 0, source.length)
        .asSequence()
        .map(source::get)
        .joinToString("")
}