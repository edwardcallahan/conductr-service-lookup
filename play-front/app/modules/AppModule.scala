package modules

import base.AppLifecycle
import com.google.inject.AbstractModule

class AppModule extends AbstractModule {

  def configure() = {
    bind(classOf[AppLifecycle]).asEagerSingleton()
  }
}
