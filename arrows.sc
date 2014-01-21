import scalaz.std.function._
import scalaz.syntax.arrow._

val g = ((n: Int) => n + 2) &&& ((n: Int) => n + 3)
g(1)

val f = ((n: Int) => n + 2) *** ((n: Int) => n + 3)
f(4, 5)

((n: Int) => n + 2).second(1, 2)

val h = ((n: Int) => n + 2) >>> ((n: Int) => n + 3)
h(1)
