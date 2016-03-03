import scalaz.{Free, Functor, Monad, ~>}
import scala.collection.mutable.{Map => MMap}
import scalaz.std.option._
// http://programmers.stackexchange.com/questions/242795/what-is-the-free-monad-interpreter-pattern

sealed trait Lang[+R]
case class Get[R](key: String, next: String => R) extends Lang[R]
case class Set[R](key: String, value: String, next: R) extends Lang[R]
case object End extends Lang[Nothing]

// TODO can we use Yoneda to get the free functor?
implicit val langFunctor = new Functor[Lang] {
  def map[A, B](fa: Lang[A])(f: A => B): Lang[B] = fa match {
    case Get(key, next) => Get(key, f compose next)
    case Set(key, value, next) => Set(key, value, f(next))
    case End => End
  }
}

def get(key: String) = Free.liftF[Lang, String](Get(key, identity))
def set(key: String, value: String) = Free.liftF[Lang, Unit](Set(key, value, ()))
def end = Free.liftF[Lang, Unit](End)
val subroutine = for {
  _ <- set("foo", "baz")
  v <- get("foo")
  _ <- set("bar", v)
} yield ()

val program = for {
  _ <- subroutine
  v <- get("foo")
  _ <- set("bam", v)
  _ <- end
  _ <- set("bif", v)
} yield ()

// http://polygonalhell.blogspot.com/2014/12/scalaz-getting-to-grips-free-monad.html

type Prog[A] = Free[Lang, A]
// immutable using foldRun

// TODO failure?
def runFn(store: Map[String, String], prog: Lang[Prog[Unit]]): (Map[String, String], Prog[Unit]) = prog match {
  case Get(key, next) => (store, next(store(key)))
  case Set(key, value, next) => (store + (key -> value), next)
  case End => (store, Free.point(()))
}

program.foldRun(Map.empty[String, String])(runFn)

// mutable using continuations

type Cont[A] = () => Option[A]

// TODO can this instance be derived instead?
implicit val contMonad = new Monad[Cont] {
  override def point[A](a: => A) = () => Some(a)
  override def bind[A, B](fa: Cont[A])(f: A => Cont[B]): Cont[B] =
    () => Monad[Option].bind(fa())(a => f(a)())
}
def runMutable(store: MMap[String, String]) = new (Lang ~> Cont) {
  override def apply[A](stmt: Lang[A]): Cont[A] = stmt match {
    case Get(key, next) => () => Some(next(store(key)))
    case Set(key, value, next) => () => { store.put(key, value) ; Some(next) }
    case End => () => None
  }
}
val s = MMap.empty[String, String]
program.foldMap(runMutable(s)).apply()
s

// immutable using transformers
// TODO research standard names for these types (State?)
type T[S, A] = S => (S, Option[A])

// TODO can this instance be derived (free) instead?
implicit def tMonad[S] = new Monad[({type t[a] = T[S, a]})#t] {
  override def point[A](a: => A) = (_, Some(a))
  override def bind[A, B](fa: T[S, A])(f: A => T[S, B]): T[S, B] = s0 => {
    val (s1, a1) = fa(s0)
    a1.map(a => f(a)(s1)).getOrElse(s1, None)
  }
}
type TS[A] = T[Map[String, String], A]

val runImmutable = new (Lang ~> TS) {
  override def apply[A](stmt: Lang[A]) = stmt match {
    case Get(key, next) => (store: Map[String, String]) => (store, Some(next(store(key))))
    case Set(key, value, next) => (store: Map[String, String]) => (store + (key -> value), Some(next))
    case End => (_, None)
  }
}
val s2 = program.foldMap(runImmutable).apply(Map.empty[String, String])

println("■")
