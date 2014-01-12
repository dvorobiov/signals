package api.common

import spray.http.StatusCode

sealed abstract class RequestVerb

case class Create[T](data: T) extends RequestVerb
case class Update[T](data: T) extends RequestVerb
case class Delete(id: String) extends RequestVerb
case class Get(id: String)
case object GetAll extends RequestVerb

case class SignalsHttpResponse[T](data: T, status: StatusCode) {
  def isSuccess: Boolean = status.isSuccess
  def isFailure: Boolean = status.isFailure
}
