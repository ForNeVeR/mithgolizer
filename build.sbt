name := "mithgolizer"

version := "0.1"

mainClass in (Compile, run) := Some("me.fornever.mithgolizer.Application")

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "org.igniterealtime.smack" % "smack" % "3.2.1",
  "org.igniterealtime.smack" % "smackx" % "3.2.1"
)
