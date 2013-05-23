package service

import data.Content
import repository.ContentRepositoryComponent

trait ContentServiceComponent {
  self: ContentRepositoryComponent =>

  val contentService = new ContentService()

  class ContentService {
    def add(content: Content) = {
      contentRepository.add(content)
    }
  }
}