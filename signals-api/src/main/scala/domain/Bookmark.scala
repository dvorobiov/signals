package domain

import org.joda.time.DateTime

case class Bookmark(
  id: Long,
  url: String,
  description: Option[String],
  tags: Seq[String],
  dateAdded: DateTime
)

case class BookmarkCreate(
  url: String,
  description: Option[String],
  tags: Seq[String]
)
