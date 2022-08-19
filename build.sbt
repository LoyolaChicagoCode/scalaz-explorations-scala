name := "scalaz-explorations-scala"

version := "0.2"

scalaVersion := "2.13.8"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:higherKinds"
)

addCompilerPlugin("org.typelevel" % "kind-projector_2.13.3" % "0.13.2")

libraryDependencies ++= Seq(
  "org.scalaz"     %% "scalaz-core"               % "7.3.6",
  "org.scalatest"  %% "scalatest"                 % "3.2.13",
  "org.scalacheck" %% "scalacheck"                % "1.16.0",
  "org.scalaz"     %% "scalaz-scalacheck-binding" % "7.3.6"
)
