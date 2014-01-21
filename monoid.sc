import scalaz.Monoid
import scalaz.std.anyVal._
import scalaz.std.string._
import scalaz.std.list._
import scalaz.syntax.monoid._ // ∅
import scalaz.syntax.semigroup._ // |+|
import scalaz.syntax.equal._

// ∅ == mzero
// |+| == ⊹ == append

def appendAll[E: Monoid](ms: Traversable[E]) =
  ms.foldLeft(∅[E])((_: E) |+| (_: E))
appendAll(Some(3))
appendAll(List(1, 2, 3))
appendAll(List(List(1, 2), List(3), List(4, 5, 6)))




∅[Int] assert_=== 0

