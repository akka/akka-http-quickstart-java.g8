lazy val akkaHttpVersion = "$akka_http_version$"
lazy val akkaVersion    = "$akka_version$"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.2"
    )),
    name := "$name$",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"         % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"       % akkaVersion,
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "org.scalatest"     %% "scalatest"         % "3.0.1"         % Test,
      "junit"              % "junit"             % "4.12"          % Test,
      "com.novocode"       % "junit-interface"   % "0.11"          % Test
    ),

    testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
  )
