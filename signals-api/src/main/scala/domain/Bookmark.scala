package domain

import org.joda.time.DateTime

case class Bookmark(
  id: Option[Long],
  url: String,
  description: Option[String],
  tags: Seq[String],
  dateAdded: DateTime
)
