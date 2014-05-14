import scalaz.Id._               // provides identity type constructor Id[X] = X
import scalaz.Monad
import scalaz.MonadPlus
import scalaz.Monoid
import scalaz.Writer
import scalaz.std.anyVal._       // provides standard instances of all basic types
import scalaz.std.string._       // provides standard instances of string
import scalaz.std.list._         // provides standard instances of list
import scalaz.std.option._       // provides standard instances of option
import scalaz.syntax.monoid._    // in addition provides ∅ = mzero
import scalaz.syntax.monadPlus._ // in addition provides ∅ = mzero
import scalaz.syntax.equal._     // provides assert_===

/*
 * This worksheet will produce various false errors in IntelliJ IDEA.
 * It works fine in sbt console, though.
 */

3.point[Id] assert_=== 3
3.point[Option] assert_=== Some(3)
3.point[List] assert_=== List(3)

// conventional non-monadic computation
{
  val m = 3
  val n = 2 * m
  n
} assert_=== 6


// the same expressed as a monadic computation (using the NOP Id monad)
(for {
  m <- 3.point[Id]
  n <- (2 * m).point[Id]
} yield n) assert_=== 6

// Option to represent failure
(for {
  m <- 3.point[Option]
  n <- (2 * m).point[Option]
} yield n) assert_=== Some(6)

// List to represent nondeterminism (the presence of multiple choices)
(for {
  m <- 3.point[List]
  n <- (2 * m).point[List]
} yield n) assert_=== List(6)

// abstracting over the specific choice of monad
def f[M[_]: Monad](k: Int) = for {
  m <- k.point[M]
  n <- (2 * m).point[M]
} yield n

// it can work!
f[Id](3) assert_=== 6
f[Option](3) assert_=== Some(6)
f[List](3) assert_=== List(6)

// filtering requires MonadPlus (conceptually, a monad that is also
// a monoid) so that there is a neutral element (like mzero) that gets
// returned if the filter fails (see tests below)
def g[M[_]: MonadPlus](k: Int) = for {
  m <- k.point[M]
  if m > 2
  n <- (2 * m).point[M]
} yield n

//g[Id](3) assert_=== 6 // compile-time error, Id is not a MonadPlus!
g[Option](2) assert_=== None
g[Option](3) assert_=== Some(6)
g[List](2) assert_=== List.empty
g[List](3) assert_=== List(6)


def g2[M[_]: MonadPlus](m0: M[Int]) = for {
  m <- m0
  if m > 2
  n <- (2 * m).point[M]
} yield n

g2(List(1, 2)) assert_=== List.empty
g2(List(1, 2, 3, 4)) assert_=== List(6, 8)

// abstracting over both the monad and the carrier type
def h[M[_]: Monad, A: Monoid](k: A) = for {
  m <- mzero[A].point[M]
  n = m |+| k
  n <- (n |+| n).point[M]
} yield n

h[Option, Int](3) assert_=== Some(6)
h[List, String]("hello") assert_=== List("hellohello")

// TODO transparently inject effects such as logging -> monad transformers

// TODO play more with the following Applicative examples

//List(3, 4) <*> List((_: Int) + 2, (_: Int) + 7)

//1.node(2.node(3.leaf, 4.leaf), 5.leaf) <*> ((_: Int) + 10).node(((_:Int) + 20).node(((_:Int) + 30).leaf), ((_:Int) + 40).leaf)

println("■")