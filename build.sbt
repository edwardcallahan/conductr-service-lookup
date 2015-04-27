import ByteConversions._
import play.PlayScala

name := "conductR-service-lookup"

lazy val commonSettings = Seq(
	version := "0.1.0-SNAPSHOT",
	scalaVersion := "2.11.6"
)	

lazy val charonShopApp = (project in file("play-front"))
  .settings(commonSettings: _*)
  .enablePlugins(JavaAppPackaging, PlayScala, ConductRPlugin) 
  .settings(
    name := "charon-shop",
    description := "Dead Man Delivery - Web Shop",
    BundleKeys.nrOfCpus := 1.0,
    BundleKeys.memory := 64.MiB,
    BundleKeys.diskSpace := 15.MB,
    BundleKeys.roles  := Set("frontend"),
    BundleKeys.endpoints := Map("charon" -> Endpoint("http",0, Set(URI("http://:9000/charon")))),
    BundleKeys.startCommand += "-Dhttp.port=$CHARON_BIND_PORT -Dhttp.address=$CHARON_BIND_IP",
    libraryDependencies ++= Dependencies.conductrPlayScala,
    javaOptions ++= Seq("-Xms64m", "-Xmx128m"),
    fork in run := true
  )

lazy val ferryService = (project in file("akka-back"))
  .settings(commonSettings: _*)
  .enablePlugins(JavaAppPackaging, ConductRPlugin)
  .settings(
    name := "ferry-boat",
    description := "River Boat Transport Services, LLC",
    BundleKeys.nrOfCpus := 1.0,
    BundleKeys.memory := 64.MiB,
    BundleKeys.diskSpace := 15.MB,
    BundleKeys.roles  := Set("backend"),
    BundleKeys.endpoints := Map(
      "ferry" -> Endpoint("http", 0, Set(URI("http://:9666/ferry")))),
    libraryDependencies ++= Dependencies.conductrAkkaScala,
    javaOptions ++= Seq("-Xms64m", "-Xmx128m"),
    fork in run := true
  )


