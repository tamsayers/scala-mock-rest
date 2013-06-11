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
import data._

@RunWith(classOf[JUnitRunner])
class ContentControllerSpec extends SpecificationWithJUnit with Mockito {
  trait TestContext extends Scope
      with ContentController
      with ContentServiceComponent
      with ContentRepositoryComponent {

    override val contentService = mock[ContentService]

    val body = "request body"
  }

  "list" should {
    "have content type application/json" in new TestContext {
      contentService.getAll returns List()

      val result = list(FakeRequest())

      contentType(result) must beSome("application/json")
    }

    "return the list of resources as JSON" in new TestContext {
      val types = List("text/xml", "application/json")
      contentService.getAll returns List(Resource("/url.com?param=value", types))

      val result = list(FakeRequest())

      contentAsString(result) mustEqual """[{"uri":"/url.com?param=value","types":["text/xml","application/json"]}]"""
    }
  }

  "add" should {

    "populate the request body content and type" in new TestContext {
      add(FakeRequest().withTextBody(body).withHeaders(("Content-Type", "text/xml")))

      there was one(contentService).add(Content("/", "text/xml", body))
    }
    "add the request path and query string to the url" in new TestContext {
      add(FakeRequest("POST", "/uri?param=value").withTextBody(body).withHeaders(("Content-Type", "text/xml")))

      there was one(contentService).add(Content("/uri?param=value", "text/xml", body))
    }
  }

  "get" should {
    "retrieve the content for the given accept type" in new TestContext {
      contentService.getFor(ContentCriteria("/uri?param=value", "text/xml")) returns TypedContent("text/xml", Some(body))

      val result = get(FakeRequest("GET", "/uri?param=value").withHeaders(("Accept", "text/xml")))

      contentAsString(result) mustEqual body
      contentType(result) must beSome("text/xml")
    }
  }
}