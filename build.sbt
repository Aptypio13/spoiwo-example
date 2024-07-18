ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "sheets-example"
  )

libraryDependencies +=  "com.norbitltd" %% "spoiwo" % "2.2.1"
