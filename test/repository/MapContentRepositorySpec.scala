package repository

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Before
import org.specs2.mutable.NameSpace
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.runner.JUnitRunner
import data._
import org.specs2.Specification

@RunWith(classOf[JUnitRunner])
class MapContentRepositorySpec
    extends SpecificationWithJUnit
    with Mockito
    with ContentRepositoryComponent {

  val dataUrl = DataUrl("path", "queryString")
  val contentToAdd = Content(dataUrl, "dataType", "content")

  trait TestContext extends Before {
    val repository = new MapContentRepository

    def before = {
      repository.add(contentToAdd)
    }
  }

  "add" should {
    "add content to the repository" in new TestContext {
      repository.data must_== Map(dataUrl -> Map(contentToAdd.dataType -> contentToAdd.content))
    }

    "add more content to the repository" in new TestContext {
      val moreContentToAdd = Content(dataUrl, "dataType2", "content2")
      repository.add(moreContentToAdd)

      repository.data must_== Map(dataUrl ->
        Map(contentToAdd.dataType -> contentToAdd.content, moreContentToAdd.dataType -> moreContentToAdd.content))
    }
  }

  "get for criteria" should {
    "return the typed content" in new TestContext {
      val criteria = ContentCriteria(dataUrl, contentToAdd.dataType)

      repository.getFor(criteria) must_== Some(contentToAdd.content)
    }

    "return None for non existent data type" in new TestContext {
      val nonExistentDataType = ContentCriteria(dataUrl, "unknown")
      repository.getFor(nonExistentDataType) must_== None
    }

    "return None for unknown url" in new TestContext {
      val nonExistentDataType = ContentCriteria(DataUrl("?", "?"), contentToAdd.dataType)
      repository.getFor(nonExistentDataType) must_== None
    }
  }
}