package controllers

import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import service.ContentServiceComponent
import repository.ContentRepositoryComponent
import org.specs2.mock.Mockito
import org.specs2.specification.Scope
import play.api.test._
import play.api.test.Helpers._
import data.DataUrl
import data.Resource
import data.Content
import org.junit.Ignore

@RunWith(classOf[JUnitRunner])
class ContentSpec extends SpecificationWithJUnit with Mockito {
  val dataUrl = DataUrl("http://url.com", "param=value")

  trait TestContext extends Scope
      with ContentController
      with ContentServiceComponent
      with ContentRepositoryComponent {

    override val contentService = mock[ContentService]
  }

  "list" should {
    "have content type application/json" in new TestContext {
      contentService.getAll returns List()

      val result = list(FakeRequest())

      contentType(result) must beSome("application/json")
    }

    "return the list of resources as JSON" in new TestContext {
      val types = List("text/xml", "application/json")
      contentService.getAll returns List(Resource(dataUrl, types))

      val result = list(FakeRequest())

      contentAsString(result) mustEqual """[{"url":"http://url.com?param=value","types":["text/xml","application/json"]}]"""
    }
  }

  "add" should {
    val request = FakeRequest("PUT", "", FakeHeaders(List(("Content-Type", List("text/xml")))), "request body")
    "add the request body content" in new TestContext {

      add(request)

      there was one(contentService).add(Content(DataUrl("", null), "", "request body"))
    }.pendingUntilFixed("work out how to pass in populated request")
  }
}