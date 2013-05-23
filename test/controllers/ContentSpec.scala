package controllers

import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import service.ContentServiceComponent
import repository.ContentRepositoryComponent

@RunWith(classOf[JUnitRunner])
class ContentSpec extends SpecificationWithJUnit {
  trait ContentTest extends ContentServiceComponent with ContentRepositoryComponent {

  }

  "the controller" should {
    "add content to the service" in {
      true
    }
  }
}