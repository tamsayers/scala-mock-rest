package service

import data.Content
import repository.ContentRepositoryComponent
import data.ContentCriteria
import data.Resource
import data.TypedContent

trait ContentServiceComponent {
  self: ContentRepositoryComponent =>

  val contentService = new ContentService()

  class ContentService {
    def add(content: Content) = contentRepository.add(content)

    def getFor(criteria: ContentCriteria): TypedContent = {
      val contentsStream = criteria.discreteTypes.toStream.map(content => TypedContent(content.acceptType, contentRepository.getFor(content)))

      contentsStream.collectFirst { case TypedContent(acceptType, Some(data)) => (acceptType, data) } match {
        case Some(i) => TypedContent(i._1, Some(i._2))
        case _ => throw new ContentNotFoundException
      }
    }

    def getAll: List[Resource] = contentRepository.getAll
  }
}