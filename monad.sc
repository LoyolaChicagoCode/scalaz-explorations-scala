import scalaz.Id                 // provides identity type constructor Id[X] = X
import scalaz.MonadPlus          // provides monad typeclass (abstraction)
import scalaz.Writer
import scalaz.std.anyVal._       // provides standard instances of all basic types
import scalaz.std.string._       // provides standard instances of string
import scalaz.std.list._         // provides standard instances of list
import scalaz.std.option._       // provides standard instances of option
import scalaz.syntax.monadPlus._ // in addition provides ∅ = mzero
import scalaz.syntax.equal._     // provides assert_===

3.point[Id] assert_=== 3
3.point[Option] assert_=== Some(3)
3.point[List] assert_=== List(3)

{
  val m = 3
  val n = 2 * m
  n
} assert_=== 6

(for {
  m <- 3.point[Id]
  n <- (2 * m).point[Id]
} yield n) assert_=== 6

(for {
  m <- 3.point[Option]
  n <- (2 * m).point[Option]
} yield n) assert_=== Some(6)

(for {
  m <- 3.point[List]
  n <- (2 * m).point[List]
} yield n) assert_=== List(6)

println("■")