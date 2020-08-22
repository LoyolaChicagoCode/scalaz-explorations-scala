name := "scalaz-explorations-scala"

version := "0.2"

scalaVersion := "2.13.3"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:higherKinds",
  "-Ypartial-unification"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core"               % "7.3.3",
  "org.scalatest"  %% "scalatest"                 % "3.2.1",
  "org.scalacheck" %% "scalacheck"                % "1.14.3",
  "org.scalaz"     %% "scalaz-scalacheck-binding" % "7.3.2"
)
