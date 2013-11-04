import sbt._
import Keys._

object Dependencies {

  // Versions
  val akkaVersion = "2.3-M1"
  val logbackVersion = "1.0.13"
  val sprayVersion = "1.2-RC2"
  val sprayJsonVersion = "1.2.5"

  // libs
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackVersion
  val sprayCan = "io.spray" % "spray-can" % sprayVersion
  val sprayRouting = "io.spray" % "spray-routing" % sprayVersion
  val sprayJson = "io.spray" %% "spray-json" % sprayJsonVersion
  val akkaTestKit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  val reactiveMongo = "org.reactivemongo" %% "reactivemongo" % "0.9"

  val joda =  "joda-time" % "joda-time" % "2.2"
  val jodaConvert = "org.joda" % "joda-convert" % "1.3.1"
  val scalatest =  "org.scalatest" % "scalatest_2.10" % "2.0.M8" % "test"
  val scalaz = "org.scalaz" %% "scalaz-core" % "7.0.4"

  val commonDependencies = Seq(scalatest, scalaz, joda, jodaConvert)

  // projects

  val apiDependencies =
    Seq(reactiveMongo, akkaActor, akkaSlf4j, logbackClassic, sprayCan, sprayRouting, sprayJson) ++ commonDependencies

  val persistenceDependencies =
    Seq() ++ commonDependencies
}