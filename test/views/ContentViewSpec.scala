package views

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.runner.JUnitRunner
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class ContentViewSpec
    extends SpecificationWithJUnit
    with Mockito {

  val body = "body text"
  val uri = "/content/path?param=value"
  val dataType = "text/xml"

  sequential

  "content" should {
    "be added for the request uri and content type" in {
      running(FakeApplication()) {
        val Some(result) = route(FakeRequest(POST, uri).withTextBody(body).withHeaders((CONTENT_TYPE, "text/xml")))

        status(result) mustEqual OK
      }.pendingUntilFixed
    }

    "then be retrievable by uri and type" in {
      val Some(result) = route(FakeRequest(GET, "/content/path").withHeaders((ACCEPT, "text/xml")))

      contentAsString(result) mustEqual body
    }.pendingUntilFixed
  }
}