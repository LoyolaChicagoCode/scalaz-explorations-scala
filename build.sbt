name := "scalaz-explorations-scala"

version := "0.0.2"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:higherKinds")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.0.5",
  "org.scalatest" % "scalatest_2.10" % "2.0",
  "org.scalacheck" %% "scalacheck" % "1.10.1",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.0.5"
)

initialCommands in console := """
                                |import scalaz._
                                |import Scalaz._
                                |""".stripMargin
