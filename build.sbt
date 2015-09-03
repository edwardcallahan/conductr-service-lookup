import ByteConversions._

name := "conductR-service-lookup"

SandboxKeys.image in Global := "conductr/conductr"
SandboxKeys.imageVersion in Global := "latest"
SandboxKeys.nrOfContainers in Global := 3

lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.7"
)

lazy val charonShopApp = (project in file("play-front"))
  .enablePlugins(PlayScala, ConductRPlugin, ConductRSandbox)
  .settings(commonSettings: _*)
  .settings(
    name := "charon-shop",
    description := "Dead Man Delivery - Web Shop",
    BundleKeys.nrOfCpus := 1.0,
    BundleKeys.memory := 64.MiB,
    BundleKeys.diskSpace := 15.MB,
    BundleKeys.roles  := Set("frontend"),
    BundleKeys.endpoints := Map("charon" -> Endpoint("http", services = Set(URI("http://:9000")))),
    BundleKeys.startCommand += "-Dhttp.port=$CHARON_BIND_PORT -Dhttp.address=$CHARON_BIND_IP",
    libraryDependencies ++= Dependencies.conductrPlayScala,
    fork in run := true,
    routesGenerator := InjectedRoutesGenerator
  )

lazy val ferryService = (project in file("akka-back"))
  .enablePlugins(ConductRPlugin, ConductRSandbox)
  .settings(commonSettings: _*)
  .settings(
    name := "ferry-boat",
    description := "River Boat Transport Services, LLC",
    BundleKeys.nrOfCpus := 1.0,
    BundleKeys.memory := 64.MiB,
    BundleKeys.diskSpace := 15.MB,
    BundleKeys.roles  := Set("backend"),
    BundleKeys.endpoints := Map(
      "ferry" -> Endpoint("http", services = Set(URI("http://:9666/ferry")))),
    libraryDependencies ++= Dependencies.conductrAkkaScala,
    fork in run := true
  )