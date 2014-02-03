val low = BigInt(1L)
val high = BigInt(10000000000L)

//low until high
def longRange(first: Long, last: Long) = new Iterator[Long] {
  private var i = first
  def hasNext = i < last
  def next = {val r = i; i += 1; r}
}

val lol = longRange(1L, 10000000000L)

val s: Stream[BigInt] = BigInt(0) #:: s.map(_ + 1)

val lol = longRange(0, Long.MaxValue) map (x => x * x)

lol drop 5 take 5 foreach println




s map (x => x * x) drop 5 take 5 foreach println





