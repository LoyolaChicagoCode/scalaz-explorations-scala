import scalaz.{Id, ~>, Free}

// https://www.dropbox.com/s/9pjla37wg3imnvg/Interpreter.pdf
// FIXME not working yet
// try to fix following the other free monad example

sealed trait Expr[+A]
case class Constant(value: Int) extends Expr[Int]
case class Plus[A](left: A, right: A) extends Expr[A]

def constant(i: Int) = Free.liftF[Expr, Int](Constant(i))
def plus[A](l: A, r: A) = Free.liftF[Expr, A](Plus(l, r))

val e = for {
  l <- constant(3)
  m <- constant(4)
  n <- plus(l, m)
} yield n

val r = for {
  n <- e
  o <- constant(5)
  p <- plus(n, o)
} yield p

object Evaluate extends (Expr ~> Id.Id) {
  import Id._
  override def apply[A](e: Expr[A]): Id[A] = e match {
    case Constant(i) => i
    case Plus(l, r) => (l, r) match {
      case p: (Int, Int) => p._1 + p._2
    }
  }
}

r.foldMap(Evaluate)

object Size extends (Expr ~> Id.Id) {
  import Id._
  override def apply[A](e: Expr[A]): Id[A] = e match {
    case Constant(i) => 1
    case Plus(l, r) => (l, r) match {
      case p: (Int, Int) => 1 + p._1 + p._2
    }
  }
}

r.foldMap(Size)
