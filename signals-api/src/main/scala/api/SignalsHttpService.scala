package api

import net.liftweb.json.ext.JodaTimeSerializers
import net.liftweb.json.{Formats, Serialization, NoTypeHints}
import spray.httpx.LiftJsonSupport
import spray.routing.{Directives}

trait SignalsHttpService extends Directives with LiftJsonSupport {
  implicit val liftJsonFormats: Formats = Serialization.formats(NoTypeHints) ++ JodaTimeSerializers.all
}
