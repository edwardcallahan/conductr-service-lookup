package base

import play.api._
import com.typesafe.conductr.bundlelib.play.Env

object Global extends GlobalSettings {
  val totalConfiguration = super.configuration ++ Configuration(Env.asConfig)

  override def configuration: Configuration =
    totalConfiguration
}

