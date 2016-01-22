name := "scalaz-explorations-scala"

version := "0.0.2"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:higherKinds")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.2.0",
  "org.scalatest" %% "scalatest" % "2.2.6",
  "org.scalacheck" %% "scalacheck" % "1.12.5",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.2.0"
)

//initialCommands in console := """
//                                |import scalaz._
//                                |import Scalaz._
//                                |""".stripMargin
