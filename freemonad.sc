import scalaz.{Monad, NaturalTransformation, Free, Functor}
import scala.collection.mutable.{Map => MMap}

// http://programmers.stackexchange.com/questions/242795/what-is-the-free-monad-interpreter-pattern

sealed trait Lang[+R]
case class Get[R](key: String, next: String => R) extends Lang[R]
case class Set[R](key: String, value: String, next: R) extends Lang[R]
case object End extends Lang[Nothing]

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

val p = for {
  _ <- set("foo", "baz")
  v <- get("foo")
  _ <- set("bar", v)
  _ <- end
  v <- get("bar")
  _ <- set("bam", v)
  _ <- end
} yield ()

// http://polygonalhell.blogspot.com/2014/12/scalaz-getting-to-grips-free-monad.html

type Prog[A] = Free[Lang, A]

// TODO failure?
def runFn(store: Map[String, String], prog: Lang[Prog[Unit]]): (Map[String, String], Prog[Unit]) = prog match {
  case Get(key, next) => (store, next(store(key)))
  case Set(key, value, next) => (store + (key -> value), next)
  case End => (store, Free.point(()))
}

p.foldRun(Map.empty[String, String])(runFn)

type Cont[A] = Function0[Option[A]]

// FIXME derive this instance?
// FIXME enable bind instead of flatMap on Option?
implicit val contInstance = new Monad[Cont] {
  override def point[A](a: => A) = () => Some(a)
  override def bind[A, B](fa: () => Option[A])(f: (A) => () => Option[B]): (() => Option[B]) = () => fa().flatMap(a => f(a)())
}

def h(store: MMap[String, String]) = new NaturalTransformation[Lang, Cont] {
  override def apply[A](stmt: Lang[A]): Cont[A] = stmt match {
    case Get(key, next) => () => Some(next(store(key)))
    case Set(key, value, next) => () => { store.put(key, value) ; Some(next) }
    case End => () => None
  }
}

val s = MMap.empty[String, String]
p.foldMap(h(s)).apply()
s

//type T[A] = Function1[(Map[String, String], Option[A], (Map[String, String], Option[A])]
//
//val h2 = new NaturalTransformation[Lang, T] {
//  def apply[A](stmt: Lang[A]) = stmt match {
//    case Get(key, next) => (store: Map[String, String], ) => (store, Some(next(store(key))))
//    case Set(key, value, next) => (store: Map[String, String]) => (store + (key -> value), Some(next))
//    case End => (_, None)
//  }
//}
//
//val s2 = p.foldMap(h2)