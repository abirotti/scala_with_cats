name := "scala_with_cats"

scalaVersion := "2.12.6"

libraryDependencies ++=
  Seq("org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.typelevel" %% "cats-core" % "1.0.0",
  "org.scalactic" %% "scalactic" % "3.0.5"
  )

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Ypartial-unification"
)
