package repository

import data._
import scala.collection.mutable.{ HashMap, SynchronizedMap }

trait ContentRepositoryComponent {
  val contentRepository: ContentRepository = new MapContentRepository()

  trait ContentRepository {
    def add(content: Content)
    def getFor(criteria: ContentCriteria): Option[String]
  }

  class MapContentRepository extends ContentRepository {
    private val dataStore = new HashMap[DataUrl, Map[String, String]] with SynchronizedMap[DataUrl, Map[String, String]]

    def data: Map[DataUrl, Map[String, String]] = dataStore.toMap

    def add(content: Content) = {
      val urlContent = if (dataStore.contains(content.dataUrl)) dataStore.get(content.dataUrl).get else Map()
      dataStore += (content.dataUrl -> (urlContent ++ Map(content.dataType -> content.content)))
    }

    def getFor(criteria: ContentCriteria): Option[String] = {
      dataStore.get(criteria.dataUrl) match {
        case Some(data) => data.get(criteria.dataType)
        case None => None
      }
    }
  }
}