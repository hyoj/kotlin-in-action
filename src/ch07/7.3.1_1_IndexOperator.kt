package ch07.ex3_1_1_IndexOperator

import java.util.*

data class Point(val x: Int, val y: Int)

operator fun Point.get(index: Int): Int {
    return when(index) {
        0 -> x
        1 -> y
        else ->
            throw IndexOutOfBoundsException("Invalid coordinate $index")
    }
}

fun main(args: Array<String>) {
    val p = Point(10, 20)
    println(p[1]) // 20

    val arr = arrayOf(1, 2, 3)
    println(arr[0]) // 1

    val map = mutableMapOf(1 to "one", 2 to "two")
    println(map[1]) // one
    map[1] = "ooone"
    println(map[1]) // ooone
}
