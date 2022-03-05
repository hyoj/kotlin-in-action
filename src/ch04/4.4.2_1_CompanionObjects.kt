package ch04.ex4_2_1_CompanionObjects

class A {
    companion object {
        fun bar() {
            println("Companion object called") // 클래스 안에 정의된 객체에 companion 을 붙여서 동반 객체를 만든다.
        }
    }
}

fun main(args: Array<String>) {
    A.bar() // 동반 객체의 멤버를 사용하는 구문은 자바의 정적 메서드 호출이나 정적 필드 사용 구문과 같다.
    A.Companion.bar() // 동반 객체도 이름을 붙일 수 있는데, 이름을 지정하지 않으면 Companion 이 기본 이름이다.
}
