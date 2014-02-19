// see also haskell.org/arrows/syntax.html

import scalaz.std.anyVal._   // provides standard instances of all basic types
import scalaz.std.tuple._    // provides standard instances of tuple types
import scalaz.std.function._ // provides standard instances of common function types
import scalaz.syntax.arrow._ // provides arrow operators used below
import scalaz.syntax.equal._ // provides assert_===
import scalaz.syntax.id._    // provides |> (forward pipe like in F#)

1 |> ((_ + 2): Int => Int) &&& (_ + 3) assert_=== (3, 4)
(4, 5) |> ((_ + 2): Int => Int) *** (_ + 3) assert_=== (6, 8)
(1, 2) |> ((_ + 2): Int => Int).second assert_=== (1, 4)
1 |> ((_ + 2): Int => Int) >>> (_ + 3) assert_=== 6

println("■")
