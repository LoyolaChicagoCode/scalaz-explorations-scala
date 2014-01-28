import scalaz.Cofree
import scalaz.Functor
import scalaz.std.anyVal._ // for Int to support ===
import scalaz.std.option._ // for Option as a Functor instance
import scalaz.std.stream._ // for Stream as a Functor instance
import scalaz.std.list._ // for List to support ===
import scalaz.syntax.functor._ // for map
import scalaz.syntax.equal._ // for assert_===
import scalaz.syntax.std.option._ // for |

/*
 * We start by defining a few familiar recursive algebraic data types in
 * Haskell syntax for conciseness:
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
 * Cofree is a universal higher-order type constructor for recursive
 * algebraic data types.
 *
 * Haskell definition of Cofree:
 * data Cofree f a = Cofree a (f (Cofree f a))
 *
 * In some discussions, this type is represented as
 * newtype Fix f = Fx (f (Fix f))
 * where Fix is the type constructor and Fx the value constructor
 * (both called Cofree in the definition above).
 *
 * Using Cofree, all of the above can be defined uniformly without explicit
 * recursion!
 *
 * In addition, we will be able to define a single catamorphism that works
 * for all types defined in this way. This catamorphism provides the familiar
 * fold for lists and a proper catamorphism (as opposed to a linearized
 * traversal) for trees.
 */

// natural numbers
type Nat = Cofree[Option, Unit]
val zero:  Option[Nat] = None
val three: Option[Nat] = Some(Cofree((), Some(Cofree((), Some(Cofree((), zero))))))

// list
type MyList[A] = Cofree[Option, A]
val nil:   Option[MyList[Int]] = None: Option[MyList[Int]]
val list3: Option[MyList[Int]] = Some(Cofree(1, Some(Cofree(2, Some(Cofree(3, nil))))))

// rose tree
type MyTree = Cofree[Stream, Int]
val tree0: Stream[MyTree] = Stream.empty
val tree3: Stream[MyTree] = Stream(Cofree(1, Stream(Cofree(2, tree0), Cofree(3, tree0))))
tree3.head.tail.map(_.head).toList assert_=== List(2, 3)

// generalized catamorphism for Cofree
def cata[S[+_], A, B](g: A => S[B] => B)(s: S[Cofree[S, A]])(implicit S: Functor[S]): S[B] = {
  def cata1(c: Cofree[S, A]): B = g(c.head)(c.tail map cata1) // catamorphism for a single node
  s map cata1 // catamorphism for nodes wrapped in functor
}

val toInt = (_: Unit) => (s: Option[Int]) => 1 + (s | 0) // add 1 for each node
cata(toInt)(zero)  assert_=== none
cata(toInt)(three) assert_=== 3.some

val lsum  = (n: Int)  => (s: Option[Int]) => n + (s | 0) // add value of each node
cata(lsum)(nil)    assert_=== none
cata(lsum)(list3)  assert_=== 6.some

val tsum  = (n: Int)  => (s: Stream[Int]) => n + s.sum   // add value of each node
cata(tsum)(tree0)  assert_=== Stream.empty
cata(tsum)(tree3)  assert_=== Stream(6)

println("done")

// TODO wrapper for making cata look like an instance method
// TODO retrofit this capability to existing types (Option, List, Tree, etc.)
// TODO examples of other morphisms
