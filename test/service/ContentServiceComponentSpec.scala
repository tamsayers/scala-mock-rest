package service

import org.junit.runner.RunWith
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.runner.JUnitRunner
import org.specs2.mock.Mockito
import repository.ContentRepositoryComponent
import data.ContentCriteria
import data.DataUrl
import data.Content

@RunWith(classOf[JUnitRunner])
class ContentServiceComponentSpec
    extends SpecificationWithJUnit
    with Mockito
    with ContentServiceComponent
    with ContentRepositoryComponent {

  override val contentRepository = mock[ContentRepository]
  val dataUrl = DataUrl("path", "query=string")
  val contentToAdd = Content(dataUrl, "dataType", "content")

  "content service" should {
    "add content to the repository" in {
      contentService.add(contentToAdd)

      there was one(contentRepository).add(contentToAdd)
    }
  }
}
