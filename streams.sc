// Reference:
// http://voidmainargs.blogspot.de/2011/08/folding-stream-with-scala.html

import scalaz.std.stream._      // provides Stream instances of suitable typeclasses
import scalaz.syntax.foldable._ // provides foldr on suitable data structures

// This does not terminate in plain Scala (without Scalaz support)!
// Stream.continually(true).foldRight(false)(_ || _)

// But this works as expected in Haskell thanks to Scalaz.
Stream.continually(true).foldr(false)(x => y => x || y)