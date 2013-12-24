import Dependencies._
import sbt._
import sbt.Keys._
import spray.revolver.RevolverPlugin._

val commonSettings = Seq(
  organization := "com.makesense",
  version := "0.1",
  scalacOptions := Seq("-unchecked", "-deprecation"),
  scalaVersion  := "2.10.3",
  resolvers ++= Seq (
    "Typesafe repo" at "http://repo.typesafe.com/typesafe/releases/",
    "Spray repo" at "http://repo.spray.io/"
  )
)

lazy val signals = Project("signals-all", file(".")).
  settings(commonSettings:_*).
  aggregate(persistence, api)

def signalProject(name: String) = Project(
  id = s"signals-$name",
  base = file(s"signals-$name")).
  settings(commonSettings:_*)


lazy val persistence = signalProject("persistence").
  settings(libraryDependencies ++= persistenceDependencies)

lazy val api = signalProject("api").
  settings(libraryDependencies ++= apiDependencies).
  settings(Revolver.settings: _*).
  dependsOn(persistence)