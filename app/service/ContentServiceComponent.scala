package service

import data.Content
import repository.ContentRepositoryComponent
import data.ContentCriteria
import data.Resource

trait ContentServiceComponent {
  self: ContentRepositoryComponent =>

  val contentService = new ContentService()

  class ContentService {
    def add(content: Content) = contentRepository.add(content)

    def getFor(criteria: ContentCriteria): String = {
      val contentsStream = criteria.discreteTypes.toStream.map(contentRepository.getFor(_))

      contentsStream.collectFirst { case Some(i) => i } match {
        case Some(content) => content
        case _ => throw new ContentNotFoundException
      }
    }

    def getAll: List[Resource] = contentRepository.getAll
  }
}