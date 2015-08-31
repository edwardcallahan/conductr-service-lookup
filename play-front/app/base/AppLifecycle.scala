package base

import javax.inject.{Inject, Singleton}
import com.typesafe.conductr.bundlelib.play.StatusService
import com.typesafe.conductr.bundlelib.play.ConnectionContext.Implicits.defaultContext
import play.api.inject.ApplicationLifecycle
import scala.concurrent.Future

@Singleton
class AppLifecycle @Inject()(lifecycle: ApplicationLifecycle) extends Logging {

  // On start..
  StatusService.signalStartedOrExit()
  log.info("Application has started..")

  // On stop..
  lifecycle.addStopHook(() => Future.successful(log.info("Application shutdown..")))
}
