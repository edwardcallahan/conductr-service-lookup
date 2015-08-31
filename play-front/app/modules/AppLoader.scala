package modules

import com.typesafe.conductr.bundlelib.play.Env
import play.api.{ApplicationLoader, Configuration}
import play.api.inject.guice._

class AppLoader extends GuiceApplicationLoader() {
  override def builder(context: ApplicationLoader.Context): GuiceApplicationBuilder = {
    val extra = Configuration(Env.asConfig)
    initialBuilder
      .in(context.environment)
      .loadConfig(extra ++ context.initialConfiguration)
      .overrides(overrides(context): _*)
  }
}