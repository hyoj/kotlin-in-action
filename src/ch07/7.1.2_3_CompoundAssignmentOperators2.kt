package ch07.ex1_2_3_CompoundAssignmentOperators2

fun main(args: Array<String>) {
//    val list = arrayListOf(1, 2) // mutable
    val list = arrayListOf("🚂", "🚡", "🚴‍♂️", "⛵") // mutable
//    list += 3
    println(list)

    val newList = list + listOf(4, 5) // immutable
    println(newList)

    println("Hello" + "World")
}
