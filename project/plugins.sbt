import sbt.Resolver.bintrayRepo

resolvers ++= Seq(
  bintrayRepo("typesafe", "maven-releases"),
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
  )

// The Play plugin

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.2")

// ConductR

addSbtPlugin("com.typesafe.conductr" % "sbt-conductr" % "1.0.0")
addSbtPlugin("com.typesafe.conductr" % "sbt-conductr-sandbox" % "1.0.5")

