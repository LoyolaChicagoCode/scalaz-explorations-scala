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




// TODO transparently inject effects such as logging -> monad transformers

// TODO play more with the following Applicative examples

//List(3, 4) <*> List((_: Int) + 2, (_: Int) + 7)

//1.node(2.node(3.leaf, 4.leaf), 5.leaf) <*> ((_: Int) + 10).node(((_:Int) + 20).node(((_:Int) + 30).leaf), ((_:Int) + 40).leaf)

println("■")