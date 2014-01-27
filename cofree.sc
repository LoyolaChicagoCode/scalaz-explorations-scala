import scalaz.Cofree
import scalaz.Functor
import scalaz.std.anyVal._ // for Int to support ===
import scalaz.std.option._ // for Option as a Functor instance
import scalaz.std.stream._ // for Stream as a Functor instance
import scalaz.std.list._ // for List to support ===
import scalaz.syntax.Ops
import scalaz.syntax.functor._ // for map
import scalaz.syntax.equal._ // for assert_===
import scalaz.syntax.std.option._ // for |

/*
 * We start by defining a few familiar recursive algebraic data types in Haskell syntax for conciseness:
 *
 * Nat is a simple type to represent natural numbers:
 * data Nat = Zero | Succ Nat
 *
 * List can be defined as a generic algebraic data type:
 * data List a = Nil | Cons a (List a)
 *
 * Rose trees can be defined similarly:
 * data Tree a = Node a (List (Tree a))
 *
 * Cofree is a universal higher-order type constructor for recursive algebraic data types.
 *
 * Haskell definition of Cofree:
 * data Cofree f a = Cofree a (f (Cofree f a))
 *
 * Using Cofree, all of the above can be defined uniformly without explicit recursion!
 * In addition, we will be able to define a single catamorphism that works for all types defined in this way.
 * This catamorphism provides the familiar fold for lists and a proper catamorphism
 * (as opposed to a linearized traversal) for trees.
 */

type Nat = Cofree[Option, Unit]
val three = Cofree((), Some(Cofree((), Some(Cofree((), None: Option[Nat])))))

// list
type MyList = Cofree[Option, Int]
val list = Cofree(1, Some(Cofree(2, Some(Cofree(3, None: Option[MyList])))))

// TODO support for zero and empty list

// rose tree
type MyTree = Cofree[Stream, Int]
val tree = Cofree(1, Stream(Cofree(2, Stream.empty: Stream[MyTree]), Cofree(3, Stream.empty: Stream[MyTree])))
tree.tail.map { case Cofree(h, _) => h } .toList assert_=== List(2, 3)

// generalized catamorphism for Cofree
def cata[S[+_], A, B](g: A => S[B] => B)(t: Cofree[S, A])(implicit S: Functor[S]): B =
  g(t.head)(t.tail.map(cata(g)))

// TODO wrapper for making cata look like an instance method

cata{ (_: Unit) => (s: Option[Int]) => 1 + (s | 0) }(three) assert_=== 3 // add 1 for each node
cata{ (n: Int)  => (s: Option[Int]) => n + (s | 0) }(list)  assert_=== 6 // add value of each node
cata{ (n: Int)  => (s: Stream[Int]) => n + s.sum   }(tree)  assert_=== 6 // add value of each node

println("done")