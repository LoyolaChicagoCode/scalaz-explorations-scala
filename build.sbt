name := "scalaz-explorations-scala"

version := "0.0.2"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:higherKinds")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.8",
  "org.scalatest" %% "scalatest" % "2.2.6",
  "org.scalacheck" %% "scalacheck" % "1.12.6",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.2.8"
)
