import scalaz.{Free, Functor}

// https://www.dropbox.com/s/9pjla37wg3imnvg/Interpreter.pdf
// FIXME not working yet
// try to fix following the other free monad example

sealed trait ExprF[+A]
case class Const(value: Int) extends ExprF[Nothing]
case class Plus[A](left: A, right: A) extends ExprF[A]

implicit val exprFunctor: Functor[ExprF] = new Functor[ExprF] {
  def map[A, B](fa: ExprF[A])(f: A => B): ExprF[B] = fa match {
    case Const(i) => Const(i)
    case Plus(l, r) => Plus(f(l), f(r))
  }
}

type Expr[A] = Free[ExprF, A]

def const(i: Int) = Free.liftF[ExprF, Int](Const(i))
def plus(l: Int, r: Int) = Free.liftF[ExprF, Int](Plus(l, r))

val r = for {
  l <- const(3)
  m <- const(4)
  n <- const(5)
  o <- plus(l, m)
  p <- plus(o, n)
} yield o

def goFn(expr: ExprF[Expr[Int]]): Expr[Int] = expr match {
  case Const(i) => Free.point(i)
  case Plus(l, r) => for { i <- l ; j <- r } yield i + j
}

r.go(goFn)