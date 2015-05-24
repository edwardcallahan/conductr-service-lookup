import sbt.Resolver.bintrayRepo

resolvers ++= Seq(
  bintrayRepo("typesafe", "maven-releases"),
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
  )

// The Play plugin

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.8")

// ConductR

addSbtPlugin("com.typesafe.conductr" % "sbt-conductr" % "0.36.0")

