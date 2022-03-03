package ch02.ex3_3_UsingWhenWithArbitraryObjects

import ch02.colors.Color
import ch02.colors.Color.*

fun mix(c1: Color, c2: Color) =
        when (setOf(c1, c2)) { // when 식의 인자로 아무 객체를 사용할 수 있다.
            setOf(RED, YELLOW) -> ORANGE // 분기 조건에 있는 객체 사이를 매치할 때는 동등성(equality)을 사용한다.
            setOf(YELLOW, BLUE) -> GREEN
            setOf(BLUE, VIOLET) -> INDIGO
            else -> throw Exception("Dirty color")
        }

fun main(args: Array<String>) {
    println(mix(BLUE, YELLOW))
}
