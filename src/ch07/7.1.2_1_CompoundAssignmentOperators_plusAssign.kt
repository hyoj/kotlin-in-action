package ch07.ex1_2_1_CompoundAssignmentOperators_plusAssign

data class Point(var x: Int, var y: Int)

/*operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
}*/

operator fun Point.plusAssign(other: Point): Unit {
    this.x = this.x + other.x
    this.y = this.y + other.y
}

fun main(args: Array<String>) {
    val point1 = Point(1, 1)
    val point2 = Point(2, 2)

    point1 += point2

    println(point1) // Point(x=3, y=3)
}
