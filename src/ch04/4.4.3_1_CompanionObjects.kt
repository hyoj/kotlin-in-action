package ch04.ex4_3_1_CompanionObjects

class Person(val name: String) {
    companion object Loader { // 동반 객체에도 이름을 붙일 수 있다.
        fun fromJSON(jsonText: String) : Person {
            // ...
            return Person("from ...")
        }
    }
}

fun main(args: Array<String>) {
    println(Person.Loader.fromJSON("...")) // 이름을 붙이면 이렇게 부를 수도 있다.
//    println(Person.Companion.fromJSON("...")) // (당연하게도) 이름을 붙이면 이렇게 부를 수 없다.
    println(Person.fromJSON("...")) // 이름을 붙여놓더라도, 여전히 동반 객체가 정의된 클래스 이름만으로 접근할 수 있다.

    // 대부분 클래스 이름을 통해 동반 객체에 속한 멤버를 참조할 수 있으므로 굳이 이름을 붙이지 않아도 된다.
}