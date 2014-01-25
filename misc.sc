import scalaz.std.anyVal._ // provides standard instances of all basic types
import scalaz.std.list._ // provides standard instances of list
import scalaz.std.option._ // provides standard instances of option
import scalaz.syntax.std.option._ // provides option syntax: x.some, none, ~ (getOrElse ∅)
import scalaz.syntax.monad._ // provides bind and applicative syntax: η/point/pure, >>=/bind, μ/join, >>/sequence, ∗/flatMap
import scalaz.syntax.equal._ // provides assert_===

~none[List[Int]] assert_=== List.empty
none[Int] | 4 assert_=== 4
some(3) ∗ some assert_=== some(3)

List(1, 2, 3) ∗ (List(_, 7)) assert_=== List(1, 7, 2, 7, 3, 7)
1.point[Option] assert_=== some(1)
1.point[List] assert_=== List(1)

import scalaz.Kleisli.kleisli
val f = kleisli(some[Int]) >=> kleisli(some[Int])
(3.some >>= f) assert_=== 3.some
(3.some >>= f) >> 4.some assert_=== 4.some

import scalaz.std.string._
3 assert_=== 3
"asdf" assert_=== "asdf"
"asdf" assert_=== "asdf"

import scalaz.syntax.id._ // provides |> (forward pipe like in F#)
1 + 2 + 3 |> {_ * 6} assert_=== 36
1 + 2 + 3 |> identity |> { 2 + _ } assert_=== 8
List(1, 2) ∘ identity assert_=== List(1, 2) // same as map :-D
