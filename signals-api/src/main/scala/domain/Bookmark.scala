package domain

import org.joda.time.DateTime
import reactivemongo.bson._
import reactivemongo.bson.BSONDateTime

case class Bookmark(
  id: BSONObjectID,
  url: String,
  description: Option[String],
  tags: Seq[String],
  creationDate: DateTime
)

object Bookmark {
  implicit object BookmarkBSONReader extends BSONDocumentReader[Bookmark] {
    def read(doc: BSONDocument): Bookmark =
      Bookmark(
        doc.getAs[BSONObjectID]("_id").get,
        doc.getAs[String]("url").get,
        doc.getAs[String]("description"),
        doc.getAs[Seq[String]]("tags").get,
        doc.getAs[BSONDateTime]("creationDate").map(dt => new DateTime(dt.value)).get)
  }
  implicit object BookmarkBSONWriter extends BSONDocumentWriter[Bookmark] {
    def write(bookmark: Bookmark): BSONDocument =
      BSONDocument(
        "id" -> bookmark.id,
        "url" -> bookmark.url,
        "description" -> bookmark.description,
        "tags" -> bookmark.tags,
        "creationDate" -> BSONDateTime(bookmark.creationDate.getMillis))
  }

  def fromBookmarkCreate(bc: BookmarkCreate): Bookmark = {
    Bookmark(BSONObjectID.generate, bc.url, bc.description, bc.tags, DateTime.now)
  }
}


case class BookmarkCreate(
  url: String,
  description: Option[String],
  tags: Seq[String]
)


