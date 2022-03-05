package ch04.ex4_2_2_CompanionObjects1

fun getFacebookName(accountId: Int) = "fb:$accountId"


/*
* 동반 객체는 자신을 둘러싼 객체의 모든 private 멤버에 접근할 수 있다.
* 이는 팩토리 패턴을 구현하기에 좋은 위치다.
*
* 클래스의 인스턴스를 생성하는 팩토리 메서드를 구현해보자.
* 주 생성자를 비공개로 만들고,
* 클래스 안에 동반 객체를 선언한다.
*
* 하지만 클래스를 확장해야만 하는 경우에는
* 동반 객체 멤버를 하위 클래스에서 오버라이드할 수 없으므로
* 여러 생성자를 사용하는 편이 더 나은 해법이다.
* */
class User private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) = User(email.substringBefore('@'))
        fun newFacebookUser(accountId: Int) = User(getFacebookName(accountId))
    }
}

fun main(args: Array<String>) {
    val subscribingUser = User.newSubscribingUser("bob@gmail.com")
    val facebookUser = User.newFacebookUser(4)
    println(subscribingUser.nickname)
    println(facebookUser.nickname)
}
