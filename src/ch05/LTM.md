# 5장. 람다로 프로그래밍

## 람다 식과 멤버 참조
### 람다 소개
람다는 다른 함수에 넘길 수 있는 작은 코드 조각을 뜻한다.  
함수형 프로그래밍에서는 함수를 값처럼 다루는 접근 방법을 택했다.  
함수형 언어에서는 함수를 직접 다른 함수에 전달할 수 있다.  

람다 식을 사용하면 함수를 선언할 필요도 없이 코드 블록을 직접 함수의 인자로 전달할 수 있다.
(자바 8에서 람다가 도입되기 전까지는 익명 함수 클래스를 사용했어야 해서 매우 번거로웠다.  
반면 이런 람다식의 도입으로 코드를 간결하게 만들 수 있게되었다!)

### 람다와 컬렉션
코틀린에서는 컬렉션을 편리하게 다루기 위한 라이브러리가 있다.  
이 라이브러리는 모든 컬렉션에 대해 가장 큰 원소 찾기(maxBy) 같은 함수를 제공한다.  
maxBy는 가장 큰 원소를 찾기 위해 비교에 사용할 값을 돌려주는 함수를 인자로 받는다.
중괄호에 둘러싸인 코드 { it.age ?: 0 }가 바로 비교에 사용할 값을 돌려주는 함수이다.

```kotlin
data class Person(val name: String,
                  val age: Int? = null)

fun main(args: Array<String>) {
    val persons = listOf(Person("Alice"),
                         Person("Bob", age = 29))

    val oldest = persons.maxBy { it.age ?: 0 }
    println("The oldest is: $oldest")
}
```

인자로 함수나 프로퍼티를 반환하는 역할을 수행하는 람다는 멤버 참조로 대치할 수 있다.
```kotlin
people.maxBy(Person::age)
```

### 람다 식의 문법
람다 식은 항상 중괄호 사이에 위치한다.
인자 목록 주변에는 괄호가 없다.
화살표(->)가 인자 목록과 람다 본문을 구분해준다.
```kotlin
{ x: int, y: int -> x + y }
```

람다 식을 변수에 저장할 수가 았다.
그러면 람다가 저장된 변수를 다른 일반 함수와 마찬가지로 다룰 수 있다.
```kotlin
fun main(args: Array<String>) {
    val sum = { x: Int, y: Int -> x + y }
    println(sum(1, 2))
}
```

람다 식을 직접 호출해도 된다. 하지만 이런 구문은 읽기 어렵고 쓸모도 없다.
이렇게 코드의 일부분을 블록으로 둘러싸 실행할 필요가 있다면 run을 사용한다.
```kotlin
fun main(args: Array<String>) {
    { println(42) }()
    run { println(42) }
}
```

아래 람다 식은 정식 문법을 모두 적용한 것인데, 이를 더욱 간결하게 만드는 과정을 살펴보자.
```kotlin
val oldest = persons.maxBy({ p: Person -> p.age })
```
컴파일러가 컨텍스트로 부터 유추할 수 있는 인자 타입은 생략할 수 있다.
```kotlin
val oldest = persons.maxBy({ p -> p.age })
```
람다의 디폴트 파라미터 이름은 it이다.
인자가 단 하나뿐이고 그 타입을 컴파일러가 추론할 수 있는 경우 it을 바로 쓸 수 있다.
```kotlin
val oldest = persons.maxBy({ it.age })
```

함수 호출 시 맨 뒤에 있는 인자가 람다 식이라면 그 람다를 괄호 밖으로 빼낼 수 있다.
```kotlin
val oldest = persons.maxBy(){ it.age }
```
또한 람다가 어떤 함수의 유일한 인자이고 괄호 뒤에 람다를 썼다면 호출 시 빈 괄호를 없애도 된다.
```kotlin
val oldest = persons.maxBy { it.age }
```

람다를 변수에 저장할 때는 파라미터 타입을 추론할 컨텍스트가 존재하지 않는다.  
따라서 이 때는 파라미터 타입을 명시해야 한다.
```kotlin
val getAge = { p: Person -> p.age }
```

본문이 여러 줄로 이뤄진 경우 본문의 맨 마지막에 있는 식이 람다의 결과 값이 된다.

### 현재 영역에 있는 변수에 접근
코틀린에서는 람다를 함수 안에서 정의하면 함수의 파라미터뿐 아니라 람다 정의의 앞에 선언된 로컬 변수까지 람다에서 모두 사용할 수 있다.
final 이 아닌 변수여도 접근이 가능하며, 변경도 할 수 있다.
(자바 메서드 안에서 무명 내부 클래스를 정의할 때 메서드 로컬 변수를 무명 내부 클래스에서 사용할 수 있는 것은 같지만  
자바는 메서드 로컬 변수가 final 일 때만 접근이 가능하다. final 이니 당연히 변경도 불가하다.)

이렇게 람다 안에서 사용하는 외부 변수를 '람다가 포획<sup>capture</sup>한 변수'라고 부른다.

### 멤버 참조
::를 사용하는 식을 멤버 참조<sup>member reference</sup>라고 부른다.  
멤버 참조는 프로퍼티나 메서드를 단 하나만 호출하는 함수 값을 만들어준다.
(멤버는 프로퍼티나 메서드이다.)
```kotlin
클래스::멤버
```
```kotlin
//val getAge = { person: Person -> person.age }
val getAge = Person::age
```

최상위에 선언된(그리고 다른 클래스의 멤버가 아닌) 함수나 프로퍼티를 참조할 수도 있다.
```kotlin
fun salute() = println("Salute!")

fun main(args: Array<String>) {
    run(::salute)
}
```

람다가 인자가 여럿인 다른 함수에게 작업을 위임하는 경우 직접 위임 함수에 대한 참조를 제공하면 편리하다.
```kotlin
val action = { person: Person, message: String -> sendEmail(person, message) } // 작업을 위임하는 방식
val nextAction = ::sendEmail // 람다 대신 멤버 참조를 사용
```

생성자 참조<sup>constructor reference</sup>를 사용하면 클래스 생성 작업을 연기하거나 저장해둘 수 있다.
```kotlin
data class Person(val name: String, val age: Int)
val createPerson = ::Person // Person 인스턴스를 만드는 동작을 값으로 정의
val p = createPerson("Alice", 29)
```

확장 함수도 멤버 함수와 똑같은 방식으로 참조할 수 있다.
```kotlin
fun Person.isAdult() = age >= 21
val predicate = Person::isAdult
```

#### 바운드 멤버 참조
코틀린 1.0에서는 클래스의 메서드나 프로퍼티에 대한 참조를 얻은 다음에 그 참조를 호출할 때 항상 인스턴스 객체를 제공해야 했다.  
코틀린 1.1부터는 바운드 멤버 참조를 지원한다. 호출 시 수신 대상 객체를 별도로 지정해 줄 필요가 없다.  
바운드 멤버 참조를 사용하면 멤버 참조를 생성할 때 클래스 인스턴스를 함꼐 저장한 다음
나중에 그 인스턴스에 대해 멤버를 호출해준다.

## 컬렉션 함수 API
함수형 프로그래밍 스타일을 사용하면 컬렉션을 다룰 떄 편리하다.  
대부분의 작업에 라이브러리 함수를 활용할 수 있고 그로 인해 코드를 아주 간결하게 만들 수 있다.

### 고차 함수(HOF, High Order Function)
함수형 프로그래밍에서는 람다나 다른 함수를 인자로 받거나 함수를 반환하는 함수를 고차함수라고 부른다.
고차 함수는 기본 함수를 조합해서 새로운 연산을 정의하거나,  
다른 고차 함수를 통해 조합된 함수를 또 조합해서 더 복잡한 연산을 쉽게 정의할 수 있다는 장점이 있다.
이런 식으로 고차 함수와 단순한 함수를 조합해서 코드를 작성하는 기법을 컴비네이터 패턴(combinator pattern)이라 부른다.
여기서 사용하는 고차 함수를 컴비네이터라고 부른다.

### 술어(Predicate)
참/거짓을 반환하는 함수를 술어라고 한다.

### filter, map: 컬렉션을 활용할 때 기반이 되는 함수
filter: 컬렉션을 이터레이션 하면서 주어진 람다에 각 원소를 넘겨서 람다가 true를 반환하는 원소만 모은다.  
map: 주어진 람다를 컬렉션의 각 원소에 적용한 결과를 모아서 새 컬렉션을 만든다.

데이터 구조 Map에서는 filterKeys와 mapKeys, filterValues, mapValues 같은 키와 값을 처리하는 함수가 따로 존재한다.

### all, any, count, find: 컬렉션에 술어 적용
all: 모든 원소가 술어를 만족하는지
any: 술어를 만족하는 원소가 하나라도 있는지
count: 술어를 만족하는 원소의 개수 구하기
find: 술어를 만족하는 원소가 하나라도 있는 경우 가장 먼저 조건을 만족한다고 확인된 원소를 반환한다.

컬렉션을 필터링한 결과의 크기가 궁금할 때 count를 사용하는 것이 좋다.
size를 사용하면 조건을 만족하는 모든 원소가 들어가는 중간 컬렉션이 생긴다.
count는 조건을 만족하는 원소의 개수만 추적하지, 조건을 만족하는 원소를 따로 저장하지 않는다.
따라서 count가 훨씬 더 효율적이다.

### groupBy

### flatMap, flatten


## 지연 계산(lazy) 컬렉션 연산

## 자바 함수형 인터페이스 활용