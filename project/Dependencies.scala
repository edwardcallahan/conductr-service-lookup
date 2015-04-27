import play.PlayImport._
import sbt._

object Dependencies{

  lazy val PlayVer = "2.3.8"
  lazy val AkkaVer  = "2.3.9"
  lazy val AkkaHttpVer = "1.0-M5"
  lazy val ConductrBundleLibVer = "0.7.1"

  lazy val conductrPlayScala = Seq(
    "com.typesafe.conductr"      %% "play-conductr-bundle-lib" 			% ConductrBundleLibVer,
    anorm,
    cache,
    ws
  )

  lazy val ferryManProject = conductrAkkaScala ++ akkaHttp ++ Seq(
    "com.typesafe.play" 		%%	"play-json" 						% PlayVer,
    "ch.qos.logback"            %	"logback-classic" 					% "1.1.2"			% "compile",
    "org.scalatest" 			%% 	"scalatest" 						% "2.2.2" 			% "test",
    "org.slf4j" 				% 	"slf4j-api" 						% "1.7.7",
    "com.typesafe" 				% 	"config" 							% "1.2.1",
    "joda-time" 				% 	"joda-time" 						% "2.4",
    "ch.qos.logback" 			% 	"logback-classic" 					% "1.1.2"
  )
  
  lazy val akkaHttp = Seq(
    "com.typesafe.akka" 		%%	"akka-stream-experimental"			% AkkaHttpVer,
    "com.typesafe.akka" 		%%	"akka-http-experimental"			% AkkaHttpVer,
    "com.typesafe.akka" 		%%	"akka-http-testkit-experimental" 	% AkkaHttpVer
   )

  lazy val conductrAkkaScala = Seq(
    "com.typesafe.akka" 		%%	"akka-actor" 						% AkkaVer,
    "com.typesafe.akka" 		%%	"akka-testkit"  					% AkkaVer,
    "com.typesafe.conductr" 	%%	"akka-conductr-bundle-lib"	 		% ConductrBundleLibVer
  )


}
