# Overview

My ongoing explorations of scalaz. For simplicity and agility, these are organized as IntelliJ IDEA worksheets.

# Motivation

There are several good reasons to use scalaz:

- You get the F#-style forward pipe operator `|>`.
- You get a proper `Tree` type (like Haskell's rose tree) and lots of other nice stuff from Haskell.
- You get common abstractions such as `Functor` and `Monoid` across predefined types
  otherwise not formally related (higher-kinded typing).
- Various excellent libraries leverage scalaz.

# Instructions

Some of these worksheets might not yet work within IntelliJ IDEA
because the Scala plugin is under active development.

You can run a worksheet on the command-line as follows:

    $ sbt test:console
    scala> :load monoid.sc

# More information

An excellent, accessible, and pragmatic scalaz intro is available here:

- [video](https://www.youtube.com/watch?v=kcfIH3GYXMI)
- [slides](https://github.com/arosien/scalaz-base-talk-201208)
- [summary/cheat sheet](https://github.com/arosien/scalaz-base-talk-201208/raw/master/scalaz-cheatsheet.pdf)
