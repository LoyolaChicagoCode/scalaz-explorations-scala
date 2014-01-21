import scalaz._
import std.anyVal._
import std.list._
import std.option._
import syntax.std.option._
import syntax.bind._
import syntax.monad._
~none[List[Any]] // ~v = v getOrElse ∅
none[Int] | 4
some(3) ∗ some
// ∘ == map (functor)
// ∗ == flatMap (monad)
// ☆ == kleisli
//λ[α, β]
//η
List(1, 2, 3) ∗ (List(_, 7))
1.point[Option]
1.point[List]
1.point[scalaz.Tree]
// monoid/semigroup
// ∅ == mzero
// |+| == ⊹ == append

//1 + 2 + 3 |> {_ * 6}
val t1 = List(1, 2) ∘ identity // same as map :-D
val f = Kleisli(some[Int]) >=> Kleisli(some[Int])
(3.some >>= f) >> 4.some


// how to embed tests in worksheets???

import syntax.equal._
import std.string._
3 assert_=== 3
"asdf" assert_=== "asdf"
3 assert_=== 4
"asdf" assert_=== "asdf"


