package repository

import data._
import scala.collection.mutable.{ HashMap, SynchronizedMap }

trait ContentRepositoryComponent {
  val contentRepository: ContentRepository = new MapContentRepository()

  trait ContentRepository {
    def add(content: Content)
    def getFor(criteria: ContentCriteria): Option[String]
    def getAll: List[Resource]
  }

  class MapContentRepository extends ContentRepository {
    private val dataStore = new HashMap[String, Map[String, String]] with SynchronizedMap[String, Map[String, String]]

    def data: Map[String, Map[String, String]] = dataStore.toMap

    def add(content: Content) = {
      val urlContent = if (dataStore.contains(content.uri)) dataStore.get(content.uri).get else Map()
      dataStore += (content.uri -> (urlContent ++ Map(content.dataType -> content.content)))
    }

    def getFor(criteria: ContentCriteria): Option[String] = {
      dataStore.get(criteria.uri) match {
        case Some(data) => data.get(criteria.acceptType)
        case None => None
      }
    }

    def getAll: List[Resource] = dataStore.map({ entry =>
      val (uri, contents) = entry
      Resource(uri, contents.keys.toList)
    }).toList
  }
}