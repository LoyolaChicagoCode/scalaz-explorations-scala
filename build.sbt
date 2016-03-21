name := "scalaz-explorations-scala"

version := "0.0.2"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:higherKinds")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.1",
  "org.scalatest" %% "scalatest" % "2.2.6",
  "org.scalacheck" %% "scalacheck" % "1.13.0",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.2.1"
)
