import sbt._
import Keys._

object Resolvers
{
    val typesafeRepo = "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
}

object Dependencies
{
    val akkaActor = "com.typesafe.akka" %% "akka-actor" % "2.5.4"
    val akkaHttp = "com.typesafe.akka" %% "akka-http-core" % "10.0.10"

    val scalaAsync = "org.scala-lang.modules" %% "scala-async" % "0.9.6"
    val playJson = "com.typesafe.play" %% "play-json" % "2.6.3"

    val scalatest = "org.scalatest" %% "scalatest" % "3.0.0" % "test"

    val akkaDependencies = Seq(akkaHttp)
    val miscDependencies = Seq(playJson, scalaAsync)
    val testDependencies = Seq(scalatest)

    val allDependencies = akkaDependencies ++ miscDependencies ++ testDependencies
}

object Reddit4s extends Build
{
    import Resolvers._

    lazy val Reddit4s =
        Project("reddit4s", file("."))
            .settings(name := "reddit4s")
            .settings(organization := "io.trosa")
            .settings(scalaVersion := "2.12.2")
            .settings(version := "0.0.1")
            .settings(resolvers ++= Seq(typesafeRepo))
            .settings(libraryDependencies ++= Dependencies.allDependencies)
            .settings(scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xlint", "-Xfatal-warnings", "-feature"))
}