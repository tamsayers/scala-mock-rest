package repository

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Before
import org.specs2.mutable.SpecificationWithJUnit
import data._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MapContentRepositorySpec
    extends SpecificationWithJUnit
    with Mockito
    with ContentRepositoryComponent {

  val uri = "path?query=string"
  val contentToAdd = Content(uri, "dataType", "content")

  trait TestContext extends Before {
    val repository = new MapContentRepository

    def before = {
      repository.add(contentToAdd)
    }
  }

  "add" should {
    "add content to the repository" in new TestContext {
      repository.data must_== Map(uri -> Map(contentToAdd.dataType -> contentToAdd.content))
    }

    "add more content to the repository" in new TestContext {
      val moreContentToAdd = Content(uri, "dataType2", "content2")
      repository.add(moreContentToAdd)

      repository.data must_== Map(uri ->
        Map(contentToAdd.dataType -> contentToAdd.content, moreContentToAdd.dataType -> moreContentToAdd.content))
    }
  }

  "get for criteria" should {
    "return the typed content" in new TestContext {
      val criteria = ContentCriteria(uri, contentToAdd.dataType)

      repository.getFor(criteria) must_== Some(contentToAdd.content)
    }

    "return None for non existent data type" in new TestContext {
      val nonExistentDataType = ContentCriteria(uri, "unknown")
      repository.getFor(nonExistentDataType) must_== None
    }

    "return None for unknown url" in new TestContext {
      val nonExistentDataType = ContentCriteria("?", contentToAdd.dataType)
      repository.getFor(nonExistentDataType) must_== None
    }
  }

  "get all" should {
    "retrieve all the resources" in new TestContext {
      val moreContentToAdd = Content(uri, "dataType2", "content2")
      repository.add(moreContentToAdd)
      val differentUrlContent = Content("new path", "dataType", "content")
      repository.add(differentUrlContent)

      repository.getAll.toSet must_== Set(Resource(uri, List(contentToAdd.dataType, moreContentToAdd.dataType)),
        Resource(differentUrlContent.uri, List(differentUrlContent.dataType)))
    }
  }
}