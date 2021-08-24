name := "scalaz-explorations-scala"

version := "0.2"

scalaVersion := "2.13.6"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:higherKinds"
)

addCompilerPlugin("org.typelevel" % "kind-projector_2.13.3" % "0.13.0")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core"               % "7.3.5",
  "org.scalatest"  %% "scalatest"                 % "3.2.9",
  "org.scalacheck" %% "scalacheck"                % "1.15.4",
  "org.scalaz"     %% "scalaz-scalacheck-binding" % "7.3.5"
)
