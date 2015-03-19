import sbt.Keys._

name := "AsyncPostgres"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= {
  Seq(
    "com.github.mauricio" %% "postgresql-async" % "0.2.15" ,
    "com.typesafe"        % "config"            % "1.2.1"
  )}
