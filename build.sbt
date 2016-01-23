name := "scalaz-explorations-scala"

version := "0.0.2"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:higherKinds")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.1.1",
  "org.scalatest" %% "scalatest" % "2.2.4",
  "org.scalacheck" %% "scalacheck" % "1.11.4",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.1.1"
)

//initialCommands in console := """
//                                |import scalaz._
//                                |import Scalaz._
//                                |""".stripMargin
