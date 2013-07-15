name := "Kiama"

version := "1.0"

scalaVersion := "2.9.2"

scalacOptions ++= Seq ("-deprecation", "-unchecked")

logLevel := Level.Warn

fork in run := false

connectInput in run := true

outputStrategy in run := Some (StdoutOutput)

parallelExecution in Test := false

javaOptions in run += "-Djline.terminal=jline.UnsupportedTerminal"

javaOptions in run += "--nojline"

libraryDependencies ++=
    Seq (
        "com.googlecode.kiama" %% "kiama" % "1.3.0",
        "com.googlecode.kiama" %% "kiama" % "1.3.0" % "test" classifier ("test"),
        "com.googlecode.kiama" %% "kiama" % "1.3.0" % "sources" classifier ("sources"),
        "junit" % "junit" % "4.10" % "test",
        "org.scalatest" %% "scalatest" % "1.8" % "test",
        "org.scalacheck" %% "scalacheck" % "1.9" % "test"
    )