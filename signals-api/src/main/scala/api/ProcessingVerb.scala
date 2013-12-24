package api

sealed abstract class RequestVerb

case class Create[T](data: T) extends RequestVerb
case class Update[T](data: T) extends RequestVerb

