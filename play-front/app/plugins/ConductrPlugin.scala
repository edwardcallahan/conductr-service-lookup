package plugins

import com.typesafe.conductr.bundlelib.play.StatusService
import com.typesafe.conductr.bundlelib.play.ConnectionContext.Implicits
import play.api.{ Application, Plugin, Logger }
import play.api.libs.concurrent.Execution

class ConductrPlugin(app: Application) extends Plugin {

  override def onStart() {
    import Implicits.defaultContext
    StatusService.signalStartedOrExit()
    Logger.info("Application has started")
  }

  override def onStop() {
    Logger.info("Application shutdown...")
  }

}

