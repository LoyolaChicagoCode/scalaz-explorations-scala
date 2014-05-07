import scalaz.Monoid             // provides monoid typeclass (abstraction)
import scalaz.std.anyVal._       // provides standard instances of all basic types
import scalaz.std.string._       // provides standard instances of string
import scalaz.std.list._         // provides standard instances of list
import scalaz.syntax.monoid._    // provides |+| = ⊹ = mappend and ∅ = mzero
import scalaz.syntax.equal._     // provides assert_===

∅[Int] assert_=== 0
3 |+| ∅[Int] assert_=== 3
∅[Int] |+| 3 assert_=== 3
3 |+| 4 assert_=== 7

∅[List[Int]] assert_=== List.empty
List(1, 2) |+| ∅[List[Int]] assert_=== List(1, 2)
∅[List[Int]] |+| List(1, 2) assert_=== List(1, 2)
List(1, 2) |+| List(3) assert_=== List(1, 2, 3)

∅[String] assert_=== ""
"hello" |+| ∅[String] assert_=== "hello"
∅[String] |+| "world" assert_=== "world"
"hello" |+| "world" assert_=== "helloworld"

def twice[T: Monoid](x: T) = x |+| x

twice(3) assert_=== 6
twice(List(1, 2)) assert_=== List(1, 2, 1, 2)
twice("hello") assert_=== "hellohello"

// TODO replicate monoid n times

def appendAll[E: Monoid](ms: Traversable[E]) =
  ms.foldLeft { ∅[E] } { _ |+| _ }

appendAll(None: Option[Int]) assert_=== 0
appendAll(Some(3)) assert_=== 3
appendAll(List(1, 2, 3)) assert_=== 6
appendAll(List(List(1, 2), List(3), List(4, 5, 6))) assert_=== (1 to 6).toList

import scalaz.std.option._ // provides standard instances of option
import scalaz.syntax.std.option._ // provides option syntax: x.some, none
import scalaz.syntax.equal._ // provides assert_===

3.some |+| 4.some assert_=== 7.some

// imports required for checking monoid laws
// using Scalaz's ScalaCheck bindings

import scalaz.scalacheck.ScalazArbitrary._
import scalaz.scalacheck.ScalaCheckBinding._
import scalaz.scalacheck.ScalazProperties._
import org.scalacheck.Arbitrary

// the monoid laws should hold for these types
monoid.laws[Int].check
monoid.laws[String].check
monoid.laws[List[Int]].check

println("■")
