package persistence

// Use H2Driver to connect to an H2 database
import scala.slick.driver.H2Driver.simple._
import org.joda.time.DateTime

// Use the implicit threadLocalSession
import Database.threadLocalSession

object Bookmarks extends Table[(Long, String, Option[String], Seq[String], DateTime)]("Bookmarks") {
  def id = column[Long]("id", O.PrimaryKey)
  def url = column[String]("url")
  def description = column[Option[String]]("description")
  def tags = column[Seq[String]]("tags")
  def dateAdded = column[DateTime]("datedAdded")
  def * = id ~ url ~ description ~ tags ~ dateAdded
}
//} {
//  case class Bookmark(
//    id: Long,
//    url: String,
//    description: Option[String],
//    tags: Seq[String],
//    dateAdded: DateTime
//  )
//}
