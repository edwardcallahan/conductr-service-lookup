package base

import play.api.Logger

/**
 * Provides a play.api.Logger to log messages within the application.
 * For each class which uses this trait a custom Logger will be created so that the logging is always scoped per class.
 */
trait Logging {
  val log = Logger(this.getClass).logger
}
