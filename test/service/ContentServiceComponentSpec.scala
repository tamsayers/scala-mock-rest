package service

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.runner.JUnitRunner
import data.Content
import repository.ContentRepositoryComponent
import data.ContentCriteria
import org.specs2.mutable.Before
import org.specs2.specification.Scope
import data.Resource
import data.TypedContent

@RunWith(classOf[JUnitRunner])
class ContentServiceComponentSpec
    extends SpecificationWithJUnit
    with Mockito {

  val uri = "/path?query=string"
  val data = Some("content")
  val contentToAdd = Content(uri, "dataType", data.get)

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
      val criteria = ContentCriteria(uri, "dataType")
      contentRepository.getFor(criteria) returns data

      contentService.getFor(criteria) must_== TypedContent("dataType", data)
    }

    "get the content for one of the specified data types" in new TestContext {
      val criteria = ContentCriteria(uri, "dataType1,dataType2")

      contentRepository.getFor(ContentCriteria(uri, "dataType2")) returns data

      contentService.getFor(criteria) must_== TypedContent("dataType2", data)
    }

    "once content is found for a type no other repository calls should be made" in new TestContext {
      val criteria = ContentCriteria(uri, "dataType1,dataType2,dataType3")

      contentRepository.getFor(ContentCriteria(uri, "dataType2")) returns data

      contentService.getFor(criteria) must_== TypedContent("dataType2", data)
      there was no(contentRepository).getFor(ContentCriteria(uri, "dataType3"))
    }

    "if no content is found a ContentNotFoundException should be thrown" in new TestContext {
      val criteria = ContentCriteria(uri, "dataType")

      contentService.getFor(criteria) must throwA[ContentNotFoundException]
    }
  }

  "get all" should {
    "return the list of resources" in new TestContext {
      val allContent = List(Resource(uri, List("")))

      contentRepository.getAll returns allContent

      contentService.getAll must_== allContent
    }
  }
}
