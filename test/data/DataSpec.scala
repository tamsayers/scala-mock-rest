package data

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.mock.Mockito

@RunWith(classOf[JUnitRunner])
class DataSpec extends SpecificationWithJUnit with Mockito {

  val uri = DataUrl("/uri", "")

  "content criteria" should {
    "return a list of criteria for the discrete data types" in {
      val mixedTypeCriteria = ContentCriteria(uri, "type1,type2,type3")

      mixedTypeCriteria.discreteTypes must_== List(ContentCriteria(uri, "type1"), ContentCriteria(uri, "type2"), ContentCriteria(uri, "type3"))
    }

    "return a list of one criteria if there is a single type" in {
      val criteria = ContentCriteria(uri, "type1")

      criteria.discreteTypes must_== List(criteria)
    }
  }

  "data url" should {
    "give its representation as a url" in {
      DataUrl("/path", "").toString mustEqual "/path"
    }
    "give its representation as a url with query string" in {
      DataUrl("/path", "param=value").toString mustEqual "/path?param=value"
    }
  }
}