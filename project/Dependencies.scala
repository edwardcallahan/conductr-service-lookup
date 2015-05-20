import play.PlayImport._
import sbt._
import sbt.Resolver.bintrayRepo

object Dependencies{

  lazy val PlayVer = "2.3.9"
  lazy val AkkaVer  = "2.3.11"
  lazy val AkkaHttpVer = "1.0-RC2"
  lazy val ConductrBundleLibVer = "0.13.0"

  lazy val conductrPlayScala = Seq(
    "com.typesafe.conductr"      %% "play23-conductr-bundle-lib"		% ConductrBundleLibVer,
    anorm,
    cache,
    ws
  )

  lazy val ferryManProject = conductrAkkaScala ++ akkaHttp ++ Seq(
    "com.typesafe.play"         %%	"play-json"                         % PlayVer,
    "ch.qos.logback"            %	"logback-classic"                   % "1.1.2"           % "compile",
    "org.scalatest"             %% 	"scalatest"                         % "2.2.4"           % "test",
    "org.slf4j"                 % 	"slf4j-api"                         % "1.7.7",
    "com.typesafe"              % 	"config"                            % "1.2.1",
    "joda-time"                 % 	"joda-time"                         % "2.4",
    "ch.qos.logback"            % 	"logback-classic"                   % "1.1.2"
  )
  
  lazy val akkaHttp = Seq(
    "com.typesafe.akka" 		%%	"akka-stream-experimental"          % AkkaHttpVer,
    "com.typesafe.akka" 		%%	"akka-http-scala-experimental"      % AkkaHttpVer,
    "com.typesafe.akka" 		%%	"akka-http-testkit-experimental"    % AkkaHttpVer
   )

  lazy val conductrAkkaScala = Seq(
    "com.typesafe.akka"         %%	"akka-actor"                        % AkkaVer,
    "com.typesafe.akka"         %%	"akka-testkit"                      % AkkaVer,
    "com.typesafe.conductr"     %%	"akka23-conductr-bundle-lib"        % ConductrBundleLibVer
  )


}
