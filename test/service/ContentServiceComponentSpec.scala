package service

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.runner.JUnitRunner
import data.Content
import data.DataUrl
import repository.ContentRepositoryComponent
import data.ContentCriteria
import org.specs2.mutable.Before
import org.specs2.specification.Scope

@RunWith(classOf[JUnitRunner])
class ContentServiceComponentSpec
    extends SpecificationWithJUnit
    with Mockito {

  val dataUrl = DataUrl("path", "query=string")
  val data = "content"
  val contentToAdd = Content(dataUrl, "dataType", data)

  trait TestContext extends Scope
      with ContentServiceComponent
      with ContentRepositoryComponent {

    override val contentRepository = mock[ContentRepository]
  }

  "add" should {
    "add content to the repository" in new TestContext {
      contentService.add(contentToAdd)

      there was one(contentRepository).add(contentToAdd)
    }
  }

  "get for criteria" should {
    "get the content for the given criteria" in new TestContext {
      val criteria = ContentCriteria(dataUrl, "dataType")
      contentRepository.getFor(criteria) returns Option(data)

      contentService.getFor(criteria) must_== data
    }

    "get the content for one of the specified data types" in new TestContext {
      val criteria = ContentCriteria(dataUrl, "dataType1,dataType2")

      contentRepository.getFor(ContentCriteria(dataUrl, "dataType2")) returns Option(data)

      contentService.getFor(criteria) must_== data
    }

    "once content is found for a type no other repository calls should be made" in new TestContext {
      val criteria = ContentCriteria(dataUrl, "dataType1,dataType2,dataType3")

      contentRepository.getFor(ContentCriteria(dataUrl, "dataType2")) returns Option(data)

      contentService.getFor(criteria) must_== data
      there was no(contentRepository).getFor(ContentCriteria(dataUrl, "dataType3"))
    }

    "if no content is found a ContentNotFoundException should be thrown" in new TestContext {
      val criteria = ContentCriteria(dataUrl, "dataType")

      contentService.getFor(criteria) must throwA[ContentNotFoundException]
    }
  }
}
