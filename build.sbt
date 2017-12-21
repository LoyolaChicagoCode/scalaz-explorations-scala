name := "scalaz-explorations-scala"

version := "0.2"

scalaVersion := "2.12.4"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:higherKinds",
  "-Ypartial-unification"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core"               % "7.2.9",
  "org.scalatest"  %% "scalatest"                 % "3.0.1",
  "org.scalacheck" %% "scalacheck"                % "1.13.4",
  "org.scalaz"     %% "scalaz-scalacheck-binding" % "7.2.9"
)
